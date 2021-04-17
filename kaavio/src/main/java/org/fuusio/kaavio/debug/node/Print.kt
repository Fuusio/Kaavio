package org.fuusio.kaavio.debug.node

import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.SingleInputNode
import org.fuusio.kaavio.Tx

/**
 * [Print] is a testing and debugging utility [org.fuusio.kaavio.Node] that has only a single input
 * of specified type. [Print] is capable of receiving values, but it does not transmit the received
 * value. A [Print] prints the received input value to [java.io.PrintStream] provided by
 * [Kaavio.out].
 */
class Print<I : Any> : SingleInputNode<I>(), Rx<I> {

    override fun onFired() {
        Kaavio.out.println(input.value.toString())
    }

    override fun onReceive(value: I) {
        input.onReceive(value)
    }

    override fun connect(transmitter: Tx<I>): Rx<I> =
        input.connect(transmitter)
}