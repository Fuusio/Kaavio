/*
 * Copyright (C) 2019 - 2022 Marko Salmela
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
package org.fuusio.kaavio.input

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.Input
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.debugger.GraphDebugger

/**
 * [DebugActionInput] implements an [Input] type which can be used for debugging the received values
 * by notifying [GraphDebugger] when ever a value is received by this [DebugInput].
 */
class DebugActionInput<I: Any>(node: Node, var name: String? = null, action: (Ctx, I) -> Unit)
    : ActionInput<I>(node, action) {

    override fun onReceive(ctx: Ctx, value: I) {
        GraphDebugger.onValueReceived(this, value)
        super.onReceive(ctx, value)
    }
}