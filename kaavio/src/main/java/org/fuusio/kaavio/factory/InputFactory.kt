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
import org.fuusio.kaavio.input.DelegateInput

/**
 * [InputFactory] define interface for factory objects that are used to create [Input]s.
 */
interface InputFactory {

    /**
     * Creates and returns an [ActionInput] for the given [node] with the given [action]-
     */
    fun <I :Any> createActionInput(node: Node, action: (I) -> Unit): ActionInput<I>

    /**
     * Creates and returns an [ActionInput] with debug functions for the given [node] with the given
     * [action].
     */
    fun <I :Any> createDebugActionInput(node: Node, action: (I) -> Unit): ActionInput<I>

    /**
     * Creates and returns an [Input] with debug functions for the given [node].
     */
    fun <I :Any> createDebugInput(node: Node): Input<I>

    /**
     * Creates and returns an [DelegateInput] for the given actual [input] and [node].
     */
    fun <I :Any> createDelegateInput(input: Input<I>, node: Node): DelegateInput<I>

    /**
     * Creates and returns an [Input] for the given [node].
     */
    fun <I :Any> createInput(node: Node): Input<I>
}