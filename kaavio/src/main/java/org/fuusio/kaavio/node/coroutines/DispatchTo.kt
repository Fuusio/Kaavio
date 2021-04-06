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
package org.fuusio.kaavio.node.coroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.fuusio.kaavio.SingleInputSingleOutputNode
import org.fuusio.kaavio.coroutines.DispatcherType

/**
 * [DispatchTo] is a [org.fuusio.kaavio.Node] that transmits the received value and succeeding
 * [org.fuusio.kaavio.graph.Graph] execution to the [kotlinx.coroutines.CoroutineDispatcher]
 * selected by the given [type] which is specified using [DispatcherType].
 */
class DispatchTo<I: Any>(private val type: DispatcherType) : SingleInputSingleOutputNode<I,I>() {

    override fun onFired() {
        context.coroutineScope.launch {
            withContext(context.dispatcher(type)) {
                output.transmit(input.value)
            }
        }
    }
}