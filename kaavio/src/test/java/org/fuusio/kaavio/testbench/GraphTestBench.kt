package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.*
import org.fuusio.kaavio.debugger.GraphDebugger
import org.fuusio.kaavio.node.debug.Probe
import org.fuusio.kaavio.Graph
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * [GraphTestBench] provides an abstract base class for implementing test cases for a concrete
 * implementation of [Graph].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class GraphTestBench<G: Graph>
    : TestBench() {

    protected lateinit var graph: G

    @BeforeAll
    fun beforeAll() {
        Kaavio.isDebugMode = true
    }

    @AfterAll
    fun afterAll() {
        Kaavio.isDebugMode = false
    }
    /**
     * Returns the test cases as a [Map] where:
     *  * The map key is a [List] of input values that are injected to node inputs returned by
     *  [injectionInputs], and
     *  * The map value is a [List] of expected output values transmitted by the node outputs
     *  returned by [probedOutputs].
     */
    protected abstract fun testCases(): Map<List<Any>, List<Any?>>

    /**
     * Returns a [List] of [org.fuusio.kaavio.Input]s for injecting test case input values.
     */
    protected abstract fun injectionInputs(): List<Input<*>>

    /**
     * Returns a [List] of [org.fuusio.kaavio.Output]s for capturing the **actual** output values to
     * be asserted against defined **expected** values.
     */
    protected abstract fun probedOutputs(): List<Output<*>>

    /**
     * Returns the [org.fuusio.kaavio.graph.Graph] implementation to be tested.
     */
    protected abstract fun graph(): G

    /**
     * This method can be used to replace actual nodes in the given [graph] with mocked ones.
     * Examples of such nodes to be replaced are:
     * * nodes accessing data stores
     * * node performing networking
     * * [org.fuusio.kaavio.node.state.LiveDataNode] nodes
     */
    protected open fun mockNodes(graph: G) {}

    @Test
    open fun test() {
        var isDebuggerStarted = false

        testCases().forEach { (inputs, outputs) ->
            graph = graph()

            if (!isDebuggerStarted) {
                isDebuggerStarted = true
                GraphDebugger.startTesting(graph)
            }

            val injectors = createInjectors()
            val probes = createProbes()

            graph.activate()
            mockNodes(graph)

            println()
            println("Injecting inputs: ${inputValuesList(inputs)}")
            for (i in 0 until 64) print("-")
            println()
            injectInputs(inputs, injectors)
            assertOutputs(outputs, probes)

            graph.dispose()
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
        assertTrue(size <= injectors.size)
        for (i in 0 until size) {
            when (val value = inputValues[i]) {
                None -> {}
                else -> injectors[i].inject(value)
            }
        }
    }

    private fun assertOutputs(expectedValues: List<Any?>, probes: List<Probe<Any>>) {
        val size = expectedValues.size
        assertEquals(size, probes.size)
        for (i in 0 until size) {
            val output = probedOutputs()[i]
            output as DebugOutput
            if (probes[i].hasValue()) {
                assertEquals(
                    expectedValues[i],
                    probes[i].latestValue,
                    "Output from ${Graph.getNodeName(output.node)} is not the expected value: [${expectedValues[i]}], but [${probes[i].latestValue}].")
            } else {
                assertTrue(
                    expectedValues[i] == null || expectedValues[i] == None,
                    "Node ${Graph.getNodeName(output.node)} should not have outputted a value.")
            }
        }
    }

    protected open fun createInjectors(): List<Injector<Any>> {
        val injectors = mutableListOf<Injector<Any>>()
        val inputs = injectionInputs()
        for (i in inputs.indices) {
            val injector = Injector<Any>()
            injector.name = "Injector${i + 1}"
            injector.onInit(graph.context)
            injectors.add(injector)
            @Suppress("UNCHECKED_CAST")
            injector.output connect inputs[i] as Rx<Any>
        }
        return injectors
    }

    protected open fun createProbes(): List<Probe<Any>> {
        val probes = mutableListOf<Probe<Any>>()
        val outputs = probedOutputs()
        for (i in outputs.indices) {
            val probe = Probe<Any>()
            probe.name = "Probe${i + 1}"
            probe.onInit(graph.context)
            probes.add(probe)
            @Suppress("UNCHECKED_CAST")
            (outputs[i] as Tx<Any>) connect probe
        }
        return probes
    }
}