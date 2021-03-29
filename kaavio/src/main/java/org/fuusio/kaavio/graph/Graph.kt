/*
 * Copyright (C) 2019 - 2021 Marko Salmela
 *
 * http://fuusio.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fuusio.kaavio.graph

import kotlinx.coroutines.CoroutineScope
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.coroutines.CoroutinesConfig
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

/**
 * A [Graph] consists of [Node]s which are executed as functions. An initialized [Graph] forms
 * a directed graph, where its [Node]s are the vertices of the graph, and the connections from node
 * outputs to node inputs are the edges of the graph.
 */
interface Graph {

    /**
     * The [GraphContext] for this instance of [Graph].
     */
    val context: GraphContext

    /**
     * The [CoroutinesConfig] for this instance of [Graph].
     */
    val coroutinesConfig: CoroutinesConfig

    /**
     * A [CoroutineScope] to be used by the [org.fuusio.kaavio.Node]s of the [Graph].
     */
    val coroutineScope: CoroutineScope

    /**
     * An instance of [Graph] needs to be activated using this function before it can be used.
     */
    fun activate()

    /**
     * An instance of [Graph] needs to be disposed when it is no more needed, for instance,
     * to clean coroutine scopes.
     */
    fun dispose()

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
    fun onInitialize()

    /**
     * An implementation of this function can perform the addition finalization for this [Graph].
     * This function is invoked by function [dispose]. This default implementation of this function
     * does nothing.
     */
    fun onDispose()

    companion object {
        /**
         * Attach [Graph] to the [Node] it contains.
         */
        fun attachNodesToGraph(graph: Graph) {
            getNodes(graph).forEach { node -> node.onInit(graph.context) }
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
                        if (value.name == value::class.simpleName!!) {
                            value.name = field.name
                        }
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
