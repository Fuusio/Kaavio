package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.Tx
import org.fuusio.kaavio.node.debug.Probe
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * [TwoInputsNodeTestBench] provides an abstract base class for implementing a test class
 * for a [Node] that have two inputs of types [I1], [I2] and a single output of type [O].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class TwoInputsNodeTestBench<I1: Any, I2: Any, O: Any> : SingleOutputNodeTestBench<O>() {

    /**
     * Returns a [Map] of test case entries where the key is a [List] containing the input values
     * (of types [I1], [I2]) and the value is the expected output (of type [O]).
     */
    protected abstract fun testCases(): Map<List<Any>, ValueOption<O>>

    /**
     * Creates the instance of [Node] for testing and connects it to given [injector1], [injector2],
     * and [probe]. The first two are used to inject the input values to the created node and
     * the [probe] is used to receive the output produced by the node.
     */
    protected abstract fun node(injector1: Tx<I1>, injector2: Tx<I2>, probe: Rx<O>): Node

    @Suppress("UNCHECKED_CAST")
    @Test
    fun executeTestCases() {
        val injector1 = Injector<I1>()
        val injector2 = Injector<I2>()
        injector1.name = "Injector1"
        injector2.name = "Injector2"
        val probe = Probe<O>()
        val node = node(
            injector1.output as DebugOutput<I1>,
            injector2.output as DebugOutput<I2>,
            probe.input as DebugInput<O>)
        getInputAndOutputNames(node)
        testCases().forEach { (inputs, valueOption) ->
            injector1.inject(inputs[0] as I1)
            injector2.inject(inputs[1] as I2)
            assertTestCase(node, probe, listOf(inputs[0], inputs[1]), valueOption)
        }
    }
}