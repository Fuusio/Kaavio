package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.*
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
        Kaavio.isDebugMode = true
        testCases().forEach { (inputs, outputs) ->
            graph = graph()
            mockNodes(graph)
            val injectors = createInjectors()
            val probes = createProbes()
            graph.activate()

            injectInputs(inputs, injectors)
            assertOutputs(outputs, probes)
        }
        Kaavio.isDebugMode = false
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
            probes.add(probe)
            @Suppress("UNCHECKED_CAST")
            (outputs[i] as Tx<Any>) connect probe
        }
        return probes
    }

    protected fun inputValues(vararg values: Any): List<Any> = values.toList()

    protected fun outputValues(vararg values: Any?): List<Any?> = values.toList()

    protected fun inputs(vararg inputs: Input<*>): List<Input<*>> = inputs.toList()

    protected fun outputs(vararg outputs: Output<*>): List<Output<*>> = outputs.toList()

    object None
}