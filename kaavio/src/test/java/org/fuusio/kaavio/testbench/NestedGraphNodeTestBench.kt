package org.fuusio.kaavio.testbench

import io.mockk.mockk
import org.fuusio.kaavio.*
import org.fuusio.kaavio.debug.GraphDebugger
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.graph.Graph
import org.fuusio.kaavio.graph.GraphContext
import org.fuusio.kaavio.node.graph.NestedGraphNode
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.fuusio.kaavio.output.DelegateOutput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * [NestedGraphNodeTestBench] provides an abstract base class for implementing [NodeTestBench] for
 * a concrete implementation of [NestedGraphNode].
 */
abstract class NestedGraphNodeTestBench<N : NestedGraphNode<*>> : NodeTestBench() {

    protected lateinit var node: N

    /**
     * Returns the test cases as a [Map] where:
     *  * The map key is a [List] of input values that are injected to node inputs returned by
     *  [injectionInputs], and
     *  * The map value is a [List] of expected output values transmitted by the node outputs
     *  returned by [probedOutputs].
     */
    protected abstract fun testCases(): Map<List<Any>, List<Any?>>

    /**
     * Returns a [List] of [org.fuusio.kaavio.Input]s of the tested [NestedGraphNode] node for
     * injecting test case input values.
     */
    protected abstract fun injectionInputs(): List<Input<*>>

    /**
     * Returns a [List] of [org.fuusio.kaavio.Output]s of the tested [NestedGraphNode] node for
     * capturing the **actual** output values to be asserted against defined **expected** values.
     */
    protected abstract fun probedOutputs(): List<Output<*>>

    /**
     * Creates and returns an instance of [NestedGraphNode] of type [N]
     */
    abstract fun node(): N

    /**
     * This method can be used to replace actual nodes of the encapsulated [Graph] with mocked ones.
     * Examples of such nodes to be replaced are:
     * * nodes accessing data stores
     * * node performing networking
     * * [org.fuusio.kaavio.node.state.LiveData] nodes
     */
    protected open fun mockNodes() {}

    @Test
    open fun test() {
        var isDebuggerStarted = false

        testCases().forEach { (inputs, outputs) ->

            node = node()

            val context = mockk<GraphContext>()
            if (!isDebuggerStarted) {
                isDebuggerStarted = true
            }

            val injectors = createInjectors(context)
            val probes = createProbes(context)

            node.onInit(context)
            mockNodes()

            println()
            println("Injecting inputs: ${inputValuesList(inputs)}")
            for (i in 0 until 64) print("-")
            println()
            injectInputs(inputs, injectors)
            assertOutputs(outputs, probes)

            node.onDispose()
        }
        GraphDebugger.endTesting()
        Kaavio.isDebugMode = false
    }

    private fun inputValuesList(inputValues: List<Any>): String {
        val valuesList = StringBuilder()
        inputValues.forEachIndexed { index, value ->
            if (valuesList.isNotEmpty()) {
                valuesList.append(", ")
            }
            valuesList.append("${index + 1}: ${valueToString(value)}")
        }
        return valuesList.toString()
    }

    private fun injectInputs(inputValues: List<Any>, injectors: List<Injector<Any>>) {
        val size = inputValues.size
        Assertions.assertTrue(size <= injectors.size)
        for (i in 0 until size) {
            when (val value = inputValues[i]) {
                None -> {}
                else -> injectors[i].inject(value)
            }
        }
    }

    private fun assertOutputs(expectedValues: List<Any?>, probes: List<Probe<Any>>) {
        val size = expectedValues.size
        Assertions.assertEquals(size, probes.size)
        for (i in 0 until size) {
            val output = probedOutputs()[i]
            output as DelegateOutput
            if (probes[i].hasValue()) {
                Assertions.assertEquals(
                    expectedValues[i],
                    probes[i].latestValue,
                    "Output from ${Graph.getNodeName(output.node)} is not the expected value: [${expectedValues[i]}], but [${probes[i].latestValue}]."
                )
            } else {
                Assertions.assertTrue(
                    expectedValues[i] == null || expectedValues[i] == None,
                    "Node ${Graph.getNodeName(output.node)} should not have outputted a value."
                )
            }
        }
    }

    protected open fun createInjectors(context: GraphContext): List<Injector<Any>> {
        val injectors = mutableListOf<Injector<Any>>()
        val inputs = injectionInputs()
        for (i in inputs.indices) {
            val injector = Injector<Any>()
            injector.name = "Injector${i + 1}"
            injector.onInit(context)
            injectors.add(injector)
            @Suppress("UNCHECKED_CAST")
            injector.output connect inputs[i] as Rx<Any>
        }
        return injectors
    }

    protected open fun createProbes(context: GraphContext): List<Probe<Any>> {
        val probes = mutableListOf<Probe<Any>>()
        val outputs = probedOutputs()
        for (i in outputs.indices) {
            val probe = Probe<Any>()
            probe.name = "Probe${i + 1}"
            probe.onInit(context)
            probes.add(probe)
            @Suppress("UNCHECKED_CAST")
            (outputs[i] as Tx<Any>) connect probe
        }
        return probes
    }
}