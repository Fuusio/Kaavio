package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.*
import org.fuusio.kaavio.node.debug.Probe
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.node.debug.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * [SingleInputNodeTestBench] provides an abstract base class for implementing a test class
 * for a [Node] that have a single input of type [I] and a single output of type [O].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class SingleInputNodeTestBench<I: Any, O: Any> : SingleOutputNodeTestBench<O>() {

    /**
     * Returns a [Map] of test case entries where input value is given as key (of type [I]) and the
     * expected output (of type [O]) is given as value.
     */
    protected abstract fun testCases(): Map<I, ValueOption<O>>

    /**
     * Creates the instance of [Node] for testing and connects it to given [injector] and [probe]. The
     * former is used to inject the input value to the created node and the latter is used to
     * receive the output produced by the node.
     */
    protected abstract fun node(injector: Tx<I>, probe: Rx<O>): Node

    @Test
    fun executeTestCases() {
        testCases().forEach { (inputValue, valueOption) ->
            val injector = Injector<I>()
            val probe = Probe<O>()
            val node = node(injector.output as DebugOutput<I>, probe.input as DebugInput<O>)
            getInputAndOutputNames(node)
            injector.inject(inputValue)
            assertTestCase(node, probe, listOf(inputValue), valueOption)
        }
    }
}