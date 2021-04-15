package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.*
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.output.DebugOutput
import org.fuusio.kaavio.util.Quadruple
import org.fuusio.kaavio.util.Quintuple
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * [FiveInputsNodeTestBench] provides an abstract base class for implementing a test class
 * for a [Node] that have two inputs of types [I1], [I2], [I3], [I4], [I5] and a single output of
 * type [O].
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class FiveInputsNodeTestBench<I1: Any, I2: Any, I3: Any, I4: Any, I5: Any, O: Any>
    : NodeTestBench<O>() {

    /**
     * Returns a [Map] of test case entries where the key is a [Quadruple] containing the input values
     * (of types [I1], [I2], [I3], [I4], [I5]) and the value is the expected output (of type [O]).
     */
    protected abstract fun testCases(): Map<Quintuple<I1, I2, I3, I4, I5>, ValueOption<O>>

    /**
     * Creates the instance of [Node] for testing and connects it to given [injector1], [injector2],
     * [injector3], [injector4], [injector5], and [probe]. The first two are used to inject
     * the input values to the created node and the [probe] is used to receive the output produced
     * by the node.
     */
    protected abstract fun node(injector1: Tx<I1>, injector2: Tx<I2>, injector3: Tx<I3>, injector4: Tx<I4>, injector5: Tx<I5>,probe: Rx<O>): Node

    @BeforeAll
    fun beforeAll() {
        Kaavio.isDebugMode = true
    }

    @AfterAll
    fun afterAll() {
        Kaavio.isDebugMode = false
    }

    @Test
    fun executeTestCases() {
        val injector1 = Injector<I1>()
        val injector2 = Injector<I2>()
        val injector3 = Injector<I3>()
        val injector4 = Injector<I4>()
        val injector5 = Injector<I5>()
        injector1.name = "Injector1"
        injector2.name = "Injector2"
        injector3.name = "Injector3"
        injector4.name = "Injector4"
        injector5.name = "Injector5"
        val probe = Probe<O>()
        val node = node(
            injector1.output as DebugOutput<I1>,
            injector2.output as DebugOutput<I2>,
            injector3.output as DebugOutput<I3>,
            injector4.output as DebugOutput<I4>,
            injector5.output as DebugOutput<I5>,
            probe.input as DebugInput<O>)
        getInputAndOutputNames(node)
        testCases().forEach { (inputs, valueOption) ->
            injector1.inject(inputs.first)
            injector2.inject(inputs.second)
            injector3.inject(inputs.third)
            injector4.inject(inputs.fourth)
            injector5.inject(inputs.fifth)
            assertTestCase(node, probe, listOf(inputs.first, inputs.second, inputs.third, inputs.fourth, inputs.fifth), valueOption)
        }
    }
}