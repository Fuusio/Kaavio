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

import org.fuusio.kaavio.SingleInputSingleOutputNode

/**
 * [Map] is a node that uses the given map function to convert the received input value to an output
 * value.
 */
class Map<I: Any, O: Any>(name: String? = null, private val mapFunction: (I) -> O)
    : SingleInputSingleOutputNode<I, O>(name) {

    override fun onFired() {
        output.transmit(mapFunction(input.value))
    }
}