package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.*
import org.fuusio.kaavio.output.DebugOutput

abstract class TestBench : KaavioTest() {

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
     * Returns a [List] of [Output]s.
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