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
package org.fuusio.kaavio.node.stream

import org.fuusio.kaavio.node.base.SingleInputSingleOutputNode

/**
 * [Filter] is node that uses the given [function] to filter which received values are further
 * transmitter via the output of the [Filter].
 */
class Filter<I : Any>(private val function: (I) -> Boolean) : SingleInputSingleOutputNode<I, I>() {

    override fun onFired() {
        val value = input.value
        if (function.invoke(value)) transmit(value)
    }
}
