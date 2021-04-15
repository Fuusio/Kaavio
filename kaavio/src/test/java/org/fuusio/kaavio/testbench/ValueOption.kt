package org.fuusio.kaavio.testbench

sealed class ValueOption<T : Any>(val value: T?)

class Value<T : Any>(value: T) : ValueOption<T>(value) {
    override fun toString() = value?.toString() ?: "null"
}

object None : ValueOption<Unit>(null) {
    override fun toString() = "none"
}

object Trigger : ValueOption<Unit>(Unit) {
    override fun toString() = "trigger"
}

fun Boolean.toValueOption() = Value(this)

fun Byte.toValueOption() = Value(this)

fun Double.toValueOption() = Value(this)

fun Float.toValueOption() = Value(this)

fun Int.toValueOption() = Value(this)

fun Long.toValueOption() = Value(this)

fun Short.toValueOption() = Value(this)

fun String.toValueOption() = Value(this)

fun Unit.toValueOption() = Value(this)