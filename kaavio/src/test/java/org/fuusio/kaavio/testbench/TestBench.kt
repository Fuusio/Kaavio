package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.Input
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.output.DebugOutput
import org.fuusio.kaavio.util.Quadruple
import org.fuusio.kaavio.util.Quintuple

abstract class TestBench {

    protected fun assertEquals(expected: Any?, actual: Any?, message: String) {
        if (expected != actual) throw Error(message)
    }

    protected fun assertFalse(boolean: Boolean, message: String) {
        if (boolean) throw Error(message)
    }

    protected fun assertTrue(boolean: Boolean, message: String) {
        if (!boolean) throw Error(message)
    }

    protected fun fail(message: String){
        throw AssertionError(message)
    }

    /**
     * Reconnects this [DebugOutput] to given [receiver]. Reconnecting can be used for debugging purposes
     * to replace actual [Node]s from a [org.fuusio.kaavio.graph.Graph] e.g. with mock nodes.
     */
    fun <O : Any> Output<O>.reconnect(receiver: Rx<O>) {
        this as DebugOutput<O>
        reconnect(receiver)
    }

    /**
     * Asserts that this [Probe] has received the specified [value].
     */
    fun Probe<*>.assertHasValue(value: Any) {
        if (!hasValue(value)) {
            org.junit.jupiter.api.fail("Probe '$name' has not received value: '$value'")
        }
    }

    /**
     * Asserts that this [Probe] has not transmitted any value to its connected [Probe].
     */
    fun Probe<*>.assertHasNoValue() {
        if (hasValue()) {
            org.junit.jupiter.api.fail("Node '$name' has received value: '$latestValue'")
        }
    }

    fun <K, V> cases(vararg pairs: Pair<K, V>): Map<K, V> = mapOf(*pairs)

    /**
     * Returns a [List] of input values.
     */
    protected fun inputValues(vararg values: Any): List<Any> = values.toList()

    /**
     * Returns a [List] of output values.
     */
    protected fun outputValues(vararg values: Any?): List<Any?> = values.toList()

    /**
     * Returns a [List] of [Input]s.
     */
    protected fun inputs(vararg inputs: Input<*>): List<Input<*>> = inputs.toList()

    /**
     * Returns a [List] of [Outputs]s.
     */
    protected fun outputs(vararg outputs: Output<*>): List<Output<*>> = outputs.toList()

    companion object {
        internal fun valueToString(value: Any?): String =
            when (value) {
                null -> "null"
                is String -> "\"$value\""
                is None -> "none"
                is Trigger -> "trigger"
                is Char -> "'$value'"
                else -> value.toString()
            }
    }
}