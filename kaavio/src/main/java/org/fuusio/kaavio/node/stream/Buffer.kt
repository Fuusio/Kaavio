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

class Buffer<I : Any>(private val capacity: Int = Int.MAX_VALUE)
    : SingleInputSingleOutputNode<I, List<I>>() {

    val flush = actionInputOf<Unit> { flush() }

    private val buffer = mutableListOf<I>()

    override fun onFired() {
        buffer.add(input.value)
        if (buffer.size >= capacity) {
            flush()
        }
    }

    fun flush() {
        val items = mutableListOf<I>()
        items.addAll(buffer)
        output.transmit(items)
        clear()
    }

    fun clear() {
        buffer.clear()
    }

    fun size(): Int = buffer.size

    fun isEmpty(): Boolean = buffer.isEmpty()

    fun isNotEmpty(): Boolean = buffer.isNotEmpty()

    fun get(isBufferCleared: Boolean = true): List<I> {
        val items = mutableListOf<I>()
        items.addAll(buffer)
        if (isBufferCleared) clear()
        return items
    }
}
