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

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.fuusio.kaavio.Graph
import org.fuusio.kaavio.coroutines.DispatcherType

/**
 * [GraphContext] is a context object for a [Graph] which is made available for all
 * [org.fuusio.kaavio.Node]s contained by the [Graph].
 */
data class GraphContext(val graph: Graph) {

    /**
     * Gets the Default [CoroutineDispatcher].
     */
    val defaultDispatcher: CoroutineDispatcher
        get() = graph.coroutinesConfig.defaultDispatcher

    /**
     * Gets the IO [CoroutineDispatcher].
     */
    val ioDispatcher: CoroutineDispatcher
        get() = graph.coroutinesConfig.ioDispatcher

    /**
     * Gets the Main [CoroutineDispatcher].
     */
    val mainDispatcher: CoroutineDispatcher
        get() = graph.coroutinesConfig.mainDispatcher

    /**
     * A [CoroutineScope] to be used by the [org.fuusio.kaavio.Node]s of the [Graph].
     */
    val coroutineScope: CoroutineScope
        get() = graph.coroutineScope

    /**
     * Returns a [CoroutineDispatcher] for the given [type] specified as [DispatcherType].
     */
    fun dispatcher(type: DispatcherType): CoroutineDispatcher =
        graph.coroutinesConfig.dispatcher(type)
}