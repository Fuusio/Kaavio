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
package org.fuusio.kaavio.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface CoroutinesConfig {

    /**
     * The [CoroutineScope] to be used within a [org.fuusio.kaavio.graph.Graph].
     */
    val graphCoroutineScope: CoroutineScope

    /**
     * The Default [CoroutineDispatcher].
     */
    val defaultDispatcher: CoroutineDispatcher

    /**
     * The IO [CoroutineDispatcher].
     */
    val ioDispatcher: CoroutineDispatcher

    /**
     * The Main [CoroutineDispatcher].
     */
    val mainDispatcher: CoroutineDispatcher

    /**
     * Returns a [CoroutineDispatcher] for the given [type] specified as [DispatcherType].
     */
    fun dispatcher(type: DispatcherType): CoroutineDispatcher
}