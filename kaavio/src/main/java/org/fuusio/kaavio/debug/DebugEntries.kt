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
package org.fuusio.kaavio.debug

import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.input.DebugActionInput
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.output.DebugOutput

sealed class DebugEntry(open val node: Node) {
    val timestamp: Long = System.currentTimeMillis()

    val timestampLabel: String
        get() = timestamp.toString() // TODO
}

data class OnActionEntry(val input: DebugActionInput<*>, val value: Any) : DebugEntry(input.node)

data class OnException(override val node: Node, val exception: Exception) : DebugEntry(node)

data class OnValueReceivedEntry(val input: DebugInput<*>, val value: Any) : DebugEntry(input.node)

data class OnValueTransmittedEntry(val output: DebugOutput<*>, val value: Any, val receiver: Rx<*>) : DebugEntry(output.node)