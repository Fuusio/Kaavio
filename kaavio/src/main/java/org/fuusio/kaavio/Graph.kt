/*
 * Copyright (C) 2019 - 2022 Marko Salmela
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
package org.fuusio.kaavio

import kotlinx.coroutines.CoroutineScope
import org.fuusio.kaavio.coroutines.CoroutinesConfig
import org.fuusio.kaavio.graph.GraphContext
import org.fuusio.kaavio.input.DebugActionInput
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.output.DebugOutput
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
     * Returns the [List] of [Node]s contained by this [Graph]. The default implementations in
     * [org.fuusio.kaavio.graph.AbstractGraph] and [org.fuusio.kaavio.graph.GraphViewModel] use
     * reflection to obtain the nodes. For improved performance, if necessary, this method can be
     * implemented to provide the nodes list explicitly.
     */
    fun getNodes(): List<Node>

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
     * Attach the given [nodes] to this [Graph]. An implementation of this method should invoke
     * [Node.onInit] is for each [Node] in the given list. The default implementations in
     * [org.fuusio.kaavio.graph.AbstractGraph] and [org.fuusio.kaavio.graph.GraphViewModel] takes
     * care of proper invoking [Node.onInit] method.
     */
    fun attachNodes(nodes: List<Node>)

    /**
     * An implementation of this abstract function should establish all then needed connections
     * between nodes.
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
         * Returns a [List] of the [Node]s consisting this [Graph]. This method uses Reflection API
         * to collect the nodes.
         */
        fun getNodes(graph: Graph): List<Node> {
            val nodes = mutableListOf<Node>()

            graph::class.memberProperties.forEach { property ->
                val field = property.javaField

                if (field != null) {
                    field.isAccessible = true
                    val value = field.get(graph)

                    if (value is Node) {
                        if (value.name == value::class.simpleName!!) {
                            value.name = field.name
                        }
                        nodes.add(value)
                        if (Kaavio.isDebugMode) {
                            getInputAndOutputNames(value)
                        }
                    }
                }
            }
            return nodes
        }

        private fun getInputAndOutputNames(node: Node) {
            node::class.memberProperties.forEach { property ->
                val field = property.javaField

                if (field != null) {
                    field.isAccessible = true

                    when (val port = field.get(node)) {
                        is DebugActionInput<*> -> { port.name = property.name }
                        is DebugInput<*> -> { port.name = property.name }
                        is DebugOutput<*> -> { port.name = property.name }
                    }
                }
            }
        }

        /**
         * Gets the name of the given [node]. If no name is given explicitly, the name is obtained
         * by using reflection from the property field that has the [Node] as assigned value .
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
