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
import org.fuusio.kaavio.graph.GraphContext

/**
 * [Node] defines an interface for all node types used in any [Graph] implementation. A [Node]
 * has zero more typed [Input]s and zero more typed [Output]. A [Node] is set to be fired when all
 * of its inputs have received a value. In such case, function [Node.onFired] is invoked to node to
 * execute its defined function. Invocation may cause output value(s) to be transmitted via
 * [Output]s.
 *
 * Essentially, a [Node] acts as a function - it maps input values to output values.
 */
interface Node {
    val context: GraphContext
    var name: String

    /**
     * Attaches the given [Input] to this [Node].
     */
    fun attachInput(input: Input<*>)

    /**
     * Invoked to initialize this [Node] with the given [GraphContext].
     */
    fun onInit(context: GraphContext)

    /**
     * Invoked when a value has been received by the given [receivingInput].
     */
    fun onInputValueReceived(receivingInput: Input<*>)

    /**
     * Invoked when all attached [Input]s have received a value.
     */
    fun onFired()
}