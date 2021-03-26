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

import org.fuusio.kaavio.factory.DefaultInputFactory
import org.fuusio.kaavio.factory.DefaultOutputFactory
import org.fuusio.kaavio.factory.InputFactory
import org.fuusio.kaavio.factory.OutputFactory

/**
 * [Kaavio] is a singleton context and configuration object for all kaavio [org.fuusio.kaavio.graph.Graph] instances.
 */
object Kaavio {
    var isDebugMode = false
    var inputFactory: InputFactory = DefaultInputFactory()
    var outputFactory: OutputFactory = DefaultOutputFactory()

    fun <I: Any> actionInput(node: Node, action: (I) -> Unit) =
        if (isDebugMode) inputFactory.createDebugActionInput(node, action) else inputFactory.createActionInput(node, action)

    fun <I: Any> input(node: Node): Input<I> =
        if (isDebugMode) inputFactory.createDebugInput(node) else inputFactory.createInput(node)

    fun <O: Any> output(node: Node): Output<O> =
        if (isDebugMode) outputFactory.createDebugOutput(node) else outputFactory.createOutput(node)
}
