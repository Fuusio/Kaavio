package org.fuusio.kaavio.node.graph

import androidx.annotation.CallSuper
import org.fuusio.kaavio.AbstractNode
import org.fuusio.kaavio.graph.Graph
import org.fuusio.kaavio.graph.GraphContext

/**
 * An implementation of [NestedGraphNode] is used to encapsulate a given [graph] as a nested graph.
 * An implementation of the class needs to:
 * * Define [org.fuusio.kaavio.Input]s that are connected
 */
abstract class NestedGraphNode<T : Graph>(val graph: T) : AbstractNode() {

    @CallSuper
    override fun onInit(context: GraphContext) {
        super.onInit(context)
        graph.activate()
    }

    override fun onFired() {}

    override fun onDispose() {
        graph.dispose()
    }
}