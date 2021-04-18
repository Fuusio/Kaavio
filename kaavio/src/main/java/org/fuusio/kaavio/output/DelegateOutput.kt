package org.fuusio.kaavio.output

import org.fuusio.kaavio.*

/**
 * A [DelegateOutput] delegates function invocations to given [output].
 */
open class DelegateOutput<O : Any>(val output: Output<O>, node: Node) : Output<O>(node) {

    override fun transmit(value: O) {
        output.transmit(value)
    }

    override fun connect(receiver: Rx<O>) {
        output.connect(receiver)
    }

    override fun connect(receivers: List<Rx<O>>) {
        output.connect(receivers)
    }

    override fun isValueCached() = output.isValueCached()
}