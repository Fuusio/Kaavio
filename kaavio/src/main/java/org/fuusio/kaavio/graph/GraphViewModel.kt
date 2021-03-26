package org.fuusio.kaavio.graph

import androidx.lifecycle.ViewModel

/**
 * A [GraphViewModel] extends [ViewModel] to provide an abstract base class for implementing view
 * models that utilize a [Graph] implementation. In a such [ViewModel] implementation,
 * [androidx.lifecycle.LiveData] objects are implemented using instances of
 * [org.fuusio.kaavio.node.state.LiveData] which also act as nodes of a graph.
 */
abstract class GraphViewModel : ViewModel(), Graph {

    /**
     * An instance of [Graph] needs to be activated using this function before it can be used.
     */
    final override fun activate() {
        Graph.attachNodesToGraph(this)
        onConnectNodes()
        onActivate()
        onInitialize()
    }

    /**
     * This function is invoked by function [activate].
     */
    override fun onActivate() {}

    /**
     * An implementation of this function can perform additional initializations for this [Graph].
     * This function is invoked by function [activate]. This default implementation of this function
     * does nothing.
     */
    override fun onInitialize() {}
}