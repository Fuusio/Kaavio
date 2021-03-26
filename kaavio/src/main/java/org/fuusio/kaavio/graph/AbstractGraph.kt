package org.fuusio.kaavio.graph

import org.fuusio.kaavio.Node

/**
 * A [AbstractGraph] provides an abstract base class for implementing a [Graph].
 */
abstract class AbstractGraph : Graph {

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


