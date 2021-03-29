package org.fuusio.kaavio.graph

/**
 * [GraphContext] is a context object for a [Graph] which is made available for all
 * [org.fuusio.kaavio.Node]s contained by the [Graph].
 */
data class GraphContext(val graph: Graph)