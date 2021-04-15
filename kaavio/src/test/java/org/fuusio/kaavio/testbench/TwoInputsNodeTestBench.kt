package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.Tx
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * [TwoInputsNodeTestBench] provides an abstract base class for implementing a test class
 * for a [Node] that have two inputs of types [I1], [I2] and a single output of type [O].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class TwoInputsNodeTestBench<I1: Any, I2: Any, O: Any> : NodeTestBench<O>() {

    /**
     * Returns a [Map] of test case entries where the key is a [Pair] containing the input values
     * (of types [I1], [I2]) and the value is the expected output (of type [O]).
     */
    protected abstract fun testCases(): Map<Pair<I1, I2>, ValueOption<O>>

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
        injector1.name = "Injector1"
        injector2.name = "Injector2"
        val probe = Probe<O>()
        val node = node(
            injector1.output as DebugOutput<I1>,
            injector2.output as DebugOutput<I2>,
            probe.input as DebugInput<O>)
        getInputAndOutputNames(node)
        testCases().forEach { (inputs, valueOption) ->
            injector1.inject(inputs.first)
            injector2.inject(inputs.second)
            assertTestCase(node, probe, listOf(inputs.first, inputs.second), valueOption)
        }
    }
}