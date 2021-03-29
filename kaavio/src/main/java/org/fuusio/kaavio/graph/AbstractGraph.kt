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

import org.fuusio.kaavio.Node

/**
 * A [AbstractGraph] provides an abstract base class for implementing a [Graph].
 */
abstract class AbstractGraph : Graph {

    private var _context: GraphContext? = null

    /**
     * Returns the [GraphContext] of this [Graph].
     */
    override val context: GraphContext
        get() = _context ?: GraphContext(this).also { _context = it }

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


