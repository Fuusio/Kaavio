package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.*
import org.fuusio.kaavio.debug.GraphDebugger
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.graph.Graph
import org.fuusio.kaavio.graph.GraphTest
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * [GraphTestBench] provides an abstract base class for implementing test cases for a concrete
 * implementation of [Graph].
 */
abstract class GraphTestBench<G: Graph>
    : GraphTest() {

    protected lateinit var graph: G

    protected abstract fun testCases(): Map<List<Any>, List<Any?>>

    protected abstract fun graph(): G

    protected abstract fun injectionInputs(): List<Input<*>>

    protected abstract fun probedOutputs(): List<Output<*>>

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

            mockNodes(graph)
            val injectors = createInjectors()
            val probes = createProbes()

            graph.activate()

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
            injectors.add(injector)
            injector.onInit(graph.context)
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
            probe.onInit(graph.context)
            probes.add(probe)
            @Suppress("UNCHECKED_CAST")
            (outputs[i] as Tx<Any>) connect probe
        }
        return probes
    }

    fun <K, V> cases(vararg pairs: Pair<K, V>): Map<K, V> = mapOf(*pairs)

    protected fun inputs(vararg values: Any): List<Any> = values.toList()

    protected fun outputs(vararg values: Any?): List<Any?> = values.toList()

    protected fun inputs(vararg inputs: Input<*>): List<Input<*>> = inputs.toList()

    protected fun outputs(vararg outputs: Output<*>): List<Output<*>> = outputs.toList()
}