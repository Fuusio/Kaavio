package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.Tx
import org.fuusio.kaavio.node.debug.Observer
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.node.debug.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * [FourInputsNodeTestBench] provides an abstract base class for implementing a test class
 * for a [Node] that have two inputs of types [I1], [I2], [I3], [I4] and a single output of type [O].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class FourInputsNodeTestBench<I1: Any, I2: Any, I3: Any, I4: Any, O: Any>
    : SingleOutputNodeTestBench<O>() {

    /**
     * Returns a [Map] of test case entries where the key is a [List] containing the input values
     * (of types [I1], [I2], [I3], [I4]) and the value is the expected output (of type [O]).
     */
    protected abstract fun testCases(): Map<List<Any>, ValueOption<O>>

    /**
     * Creates the instance of [Node] for testing and connects it to given [injector1], [injector2],
     * [injector3], [injector4], and [observer]. The first two are used to inject the input values to
     * the created node and the [observer] is used to receive the output produced by the node.
     */
    protected abstract fun node(injector1: Tx<I1>, injector2: Tx<I2>, injector3: Tx<I3>, injector4: Tx<I4>, observer: Rx<O>): Node

    @Test
    fun executeTestCases() {
        val injector1 = Injector<I1>()
        val injector2 = Injector<I2>()
        val injector3 = Injector<I3>()
        val injector4 = Injector<I4>()
        injector1.name = "Injector1"
        injector2.name = "Injector2"
        injector3.name = "Injector3"
        injector4.name = "Injector4"
        val observer = Observer<O>()
        val node = node(
            injector1.output as DebugOutput<I1>,
            injector2.output as DebugOutput<I2>,
            injector3.output as DebugOutput<I3>,
            injector4.output as DebugOutput<I4>,
            observer.input as DebugInput<O>)
        getInputAndOutputNames(node)
        testCases().forEach { (inputs, valueOption) ->
            injector1.inject(inputs[0] as I1)
            injector2.inject(inputs[1] as I2)
            injector3.inject(inputs[2] as I3)
            injector4.inject(inputs[3] as I4)
            assertTestCase(node, observer, listOf(inputs[0], inputs[1], inputs[2], inputs[3]), valueOption)
        }
    }
}