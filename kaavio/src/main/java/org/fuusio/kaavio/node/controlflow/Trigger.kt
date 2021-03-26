package org.fuusio.kaavio.node.controlflow

import org.fuusio.kaavio.SingleOutputNode

/**
 * [Trigger] is a [org.fuusio.kaavio.Node] that can be programmatically triggered to transmit
 * a [Unit] to a connected receiving [org.fuusio.kaavio.Node].
 */
class Trigger(name: String? = null) : SingleOutputNode<Unit>(name) {

    fun fire() {
        onFired()
    }

    override fun onFired() {
        output.transmit(Unit)
    }
}