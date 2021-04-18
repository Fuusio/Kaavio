package org.fuusio.kaavio.input

import org.fuusio.kaavio.Input
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.Tx

/**
 * A [DelegateInput] delegates function invocations to the given [input].
 */
open class DelegateInput<I : Any>(val input: Input<I>, node: Node) : Input<I>(node) {

    override fun cacheValue(value: I) {
        input.cacheValue(value)
    }

    override fun onReceive(value: I) {
        input.onReceive(value)
    }

    override fun connect(transmitter: Tx<I>): Rx<I> {
        return input.connect(transmitter)
    }

    override fun connect(transmitters: List<Tx<I>>) {
        input.connect(transmitters)
    }

    override fun connect(vararg transmitters: Tx<I>) {
        input.connect(*transmitters)
    }

    override fun reset() {
        input.reset()
    }

    override fun hasValue() = input.hasValue()
}