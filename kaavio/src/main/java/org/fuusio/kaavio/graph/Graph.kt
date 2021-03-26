package org.fuusio.kaavio.graph

import org.fuusio.kaavio.Node
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

/**
 * A [Graph] consists of [Node]s which are executed as functions.
 */
interface Graph {

    /**
     * An instance of [Graph] needs to be activated using this function before it can be used.
     */
    fun activate()

    /**
     * This function is invoked by function [activate].
     */
    fun onActivate() {}

    /**
     * An implementation of this abstract function should connect all the nodes.
     */
    fun onConnectNodes()

    /**
     * An implementation of this function can perform additional initializations for this [Graph].
     * This function is invoked by function [activate]. This default implementation of this function
     * does nothing.
     */
    fun onInitialize() {}

    companion object {
        /**
         * Attach [Graph] to the [Node] it contains.
         */
        fun attachNodesToGraph(graph: Graph) {
            getNodes(graph).forEach { node -> node.graph = graph }
        }

        /**
         * Returns a [List] of the [Node]s consisting this [Graph].
         */
        private fun getNodes(graph: Graph): List<Node> {
            val nodes = mutableListOf<Node>()

            graph::class.memberProperties.forEach { property ->
                val field = property.javaField

                if (field != null) {
                    field.isAccessible = true
                    val value = field[this]

                    if (value is Node) {
                        value.name = field.name
                        nodes.add(value)
                    }
                }
            }
            return nodes
        }

        /**
         * Gets the name of the given [node]. If no name is given explicitly, the name is obtained  by
         * using reflection from the property field that has the [Node] as assigned value using
         * reflection.
         */
        fun getNodeName(node: Node): String {
            if (node.name.isNotBlank())
                return node.name
            else {
                this::class.memberProperties.forEach { property ->
                    val field = property.javaField

                    if (field != null) {
                        field.isAccessible = true

                        if (field[node] == node) {
                            return field.name
                        }
                    }
                }
            }
            throw IllegalStateException()
        }
    }
}
