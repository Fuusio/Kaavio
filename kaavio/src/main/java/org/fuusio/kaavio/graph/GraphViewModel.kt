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

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.coroutines.CoroutinesConfig

/**
 * A [GraphViewModel] extends [ViewModel] to provide an abstract base class for implementing view
 * models that utilize a [Graph] implementation. In a such [ViewModel] implementation,
 * [androidx.lifecycle.LiveData] objects are implemented using instances of
 * [org.fuusio.kaavio.node.state.LiveData] which also act as nodes of a graph.
 */
abstract class GraphViewModel : ViewModel(), Graph {

    private var _context: GraphContext? = null

    private var areNodesAttached: Boolean = false

    override val context: GraphContext
        get() = _context ?: GraphContext(this).also { _context = it }

    override val coroutineScope: CoroutineScope
        get() = viewModelScope

    override val coroutinesConfig: CoroutinesConfig = Kaavio.coroutinesConfig

    final override fun activate() {
        attachNodes(getNodes())

        if (!areNodesAttached) {
            throw IllegalStateException("Nodes are not attached to Graph by invoking Graph.attachNodes(List).")
        }
        onConnectNodes()
        onActivate()
        onInitialize()
    }

    final override fun dispose() {
        onDispose()
    }

    override fun getNodes(): List<Node> =
        Graph.getNodes(this)

    @CallSuper
    override fun attachNodes(nodes: List<Node>) {
        nodes.forEach { node -> node.onInit(context) }
        areNodesAttached = true
    }

    override fun onActivate() {}

    override fun onInitialize() {}
}