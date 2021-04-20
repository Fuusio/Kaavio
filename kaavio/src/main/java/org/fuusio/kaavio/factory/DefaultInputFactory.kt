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
package org.fuusio.kaavio.factory

import org.fuusio.kaavio.input.ActionInput
import org.fuusio.kaavio.Input
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.input.DebugActionInput
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.input.DelegateInput

/**
 * [DefaultInputFactory] provides a default implementation of [InputFactory].
 */
class DefaultInputFactory: InputFactory {

    override fun <I :Any> createDebugActionInput(node: Node, action: (I) -> Unit) =
        DebugActionInput(node, action = action)

    override fun <I :Any> createDebugInput(node: Node) =
        DebugInput<I>(node)

    override fun <I : Any> createDelegateInput(input: Input<I>, node: Node) =
        DelegateInput(input, node)

    override fun <I :Any> createActionInput(node: Node, action: (I) -> Unit) =
        ActionInput(node, action)

    override fun <I :Any> createInput(node: Node) = Input<I>(node)
}