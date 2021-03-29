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

import org.fuusio.kaavio.*
import org.fuusio.kaavio.graph.Graph
import org.fuusio.kaavio.input.DebugActionInput
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.output.DebugOutput

/**
 * [GraphDebugger] is a utility for graph debugging.
 */
object GraphDebugger {

    private const val DEFAULT_INPUT_NAME = "input"
    private const val DEFAULT_OUTPUT_NAME = "output"

    private fun isDebuggingEnabled() = Kaavio.isDebugMode

    val debugEntries = mutableListOf<DebugEntry>()

    fun onValueReceived(input: DebugInput<*>, value: Any) {
        if (isDebuggingEnabled()) {
            debugEntries.add(OnValueReceivedEntry(input, value))
        }
    }

    fun onValueReceived(input: DebugActionInput<*>, value: Any) {
        if (isDebuggingEnabled()) {
            debugEntries.add(OnActionEntry(input, value))
        }
    }

    fun onValueTransmitted(output: DebugOutput<*>, value: Any, receiver: Rx<*>) {
        if (isDebuggingEnabled()) {
            debugEntries.add(OnValueTransmittedEntry(output, value, receiver))

            val inputNode = when (receiver) {
                is Node -> receiver
                is Input -> receiver.node
                else -> throw IllegalStateException()
            }
            val inputName = DEFAULT_INPUT_NAME // TODO
            val inputNodeName = getNodeName(inputNode)
            val outputNodeName = getNodeName(output.node)
            val outputName = DEFAULT_OUTPUT_NAME // TODO
            val valueString = formatValueString(value)
            val senderName = if (outputName == DEFAULT_OUTPUT_NAME) "[$outputNodeName]" else "[$outputNodeName.$outputName]"
            val receiverName = if (inputName == DEFAULT_INPUT_NAME) "[$inputNodeName]" else "[$inputNodeName.$inputName]"

            println("$senderName - $valueString -> $receiverName")
        }
    }

    private fun getNodeName(node: Node): String {
        val name = node.name
        return when {
            name.isNotBlank() -> name
            else -> Graph.getNodeName(node)
        }
    }

    private fun formatValueString(value: Any) =
        when(value) {
            is String -> "\"$value\""
            is Char -> "'$value'"
            else -> value.toString()
        }

    fun clearDebugEntries() {
        debugEntries.clear()
    }
}