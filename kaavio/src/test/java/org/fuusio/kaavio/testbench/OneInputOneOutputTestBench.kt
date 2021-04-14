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
 * [OneInputOneOutputTestBench] provides an abstract base class for implementing a test class
 * for a [Node] that have a single input of type [I] and a single output of type [O].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class OneInputOneOutputTestBench<I: Any, O: Any> : KaavioTest() {

    /**
     * Returns a [Map] of test case entries where input value is given as key (of type [I]) and the
     * expected output (of type [O]) is given as value.
     */
    protected abstract fun testCases(): Map<I, O>

    /**
     * Creates the instance of [Node] for testing and connects it to given [injector] and [probe]. The
     * former is used to inject the input value to the created node and the latter is used to
     * receive the output produced by the node.
     */
    protected abstract fun node(injector: Tx<I>, probe: Rx<O>): Node

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
        val injector = Injector<I>()
        val probe = Probe<O>()
        val node = node(injector.output as DebugOutput<I>, probe.input as DebugInput<O>)

        testCases().forEach { (input, output) ->
            injector.inject(input)
            val value = probe.latestValue
            assertTrue(probe.hasValue(), "Node '${Graph.getNodeName(node)}' did not output a value.")
            assertEquals(
                output,
                value,
                "Test case for node '${Graph.getNodeName(node)}' failed: For input value '$input', output value '$output' is expected."
            )
        }
    }
}