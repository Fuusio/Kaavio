package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.*
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.graph.Graph
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * [TwoInputsOneOutputTestBench] provides an abstract base class for implementing a test class
 * for a [Node] that have two inputs of types [I1], [I2] and a single output of type [O].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class TwoInputsOneOutputTestBench<I1: Any, I2: Any, O: Any> : KaavioTest() {

    /**
     * Returns a [Map] of test case entries where the key is a [Pair] containing the input values
     * (of types [I1], [I2]) and the value is the expected output (of type [O]).
     */
    protected abstract fun testCases(): Map<Pair<I1, I2>, O>

    /**
     * Creates the instance of [Node] for testing and connects it to given [injector1], [injector2],
     * and [probe]. The first two are used to inject the input values to the created node and
     * the [probe] is used to receive the output produced by the node.
     */
    protected abstract fun node(injector1: Tx<I1>, injector2: Tx<I2>, probe: Rx<O>): Node

    @BeforeAll
    fun beforeAll() {
        Kaavio.isDebugMode = true
    }

    @AfterAll
    fun afterAll() {
        Kaavio.isDebugMode = false
    }

    @Test
    fun test() {
        val injector1 = Injector<I1>()
        val injector2 = Injector<I2>()
        val probe = Probe<O>()
        val node = node(
            injector1.output as DebugOutput<I1>,
            injector2.output as DebugOutput<I2>,
            probe.input as DebugInput<O>)

        testCases().forEach { (inputs, output) ->
            injector1.inject(inputs.first)
            injector2.inject(inputs.second)
            val value = probe.latestValue
            assertTrue(probe.hasValue(), "Node '${Graph.getNodeName(node)}' did not output a value.")
            assertEquals(
                output,
                value,
                "Test case for node '${Graph.getNodeName(node)}' failed: For input values '${inputs.first}' and '${inputs.second}', output value '$output' is expected."
            )
        }
    }
}