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

/**
 * [ActionInput] is an [Input] which, when a value is received, executes he given [action]
 * function without notifying the bound [Node] about the received value.
 */
open class ActionInput<I: Any>(node: Node, private val action: (I) -> Unit) : Input<I>(node) {

    override fun onReceive(value: I) {
        action(value)
    }

    /**
     * [ActionInput] is interpreted to always to have value.
     */
    override fun hasValue(): Boolean  = true
}
