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