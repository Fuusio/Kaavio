package org.fuusio.kaavio.output

import org.fuusio.kaavio.*

/**
 * A [DelegateOutput] transmits the value it received from the connected [output].
 */
open class DelegateOutput<O : Any>(val output: Output<O>, node: Node) : Output<O>(node) {

    override fun connect(receiver: Rx<O>) {
        output.connect(receiver)
    }

    override fun connect(receivers: List<Rx<O>>) {
        output.connect(receivers)
    }
}