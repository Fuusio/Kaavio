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
package org.fuusio.kaavio

import org.fuusio.kaavio.graph.Graph

/**
 * [Node] is the abstract base class for all node types used in any [Graph] implementation.
 */
abstract class Node(name: String? = null) {
    private var _name: String? = name

    var graph: Graph? = null

    var name: String
        get() = _name ?: this::class.simpleName!!
        set(value) {
            _name = value
        }

    private val inputs: MutableList<Input<*>> = mutableListOf()

    internal open fun addInput(input: Input<*>) {
        inputs.add(input)
    }

    internal open fun onInputReceived() {
        inputs.forEach { input -> if (!input.hasValue()) return }
        onFired()
    }

    abstract fun onFired()

    companion object {
        fun <I :Any> actionInputOf(node: Node, action: (I) -> Unit): Input<I> = Kaavio.actionInput(node, action)
        fun <I :Any> inputOf(node: Node): Input<I> = Kaavio.input(node)
        fun <O :Any> outputOf(node: Node): Output<O> = Kaavio.output(node)
    }
}