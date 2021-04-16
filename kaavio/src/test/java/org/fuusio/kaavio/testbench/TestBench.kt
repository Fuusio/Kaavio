package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.debug.node.Probe
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

    fun <T1, T2> pair(first: T1, second: T2): Pair<T1, T2> = Pair(first, second)

    fun <T1, T2, T3> triple(first: T1, second: T2, third: T3): Triple<T1, T2, T3> = Triple(first, second, third)

    fun <T1, T2, T3, T4> quadruple(first: T1, second: T2, third: T3, fourth: T4): Quadruple<T1, T2, T3, T4> = Quadruple(first, second, third, fourth)

    fun <T1, T2, T3, T4, T5> quintuple(first: T1, second: T2, third: T3, fourth: T4, fifth: T5): Quintuple<T1, T2, T3, T4, T5> = Quintuple(first, second, third, fourth, fifth)

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