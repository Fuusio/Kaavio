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
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * [DebugEntry] is a sealed class for all various types of entry object used for recording debugger
 * events.
 */
sealed class DebugEntry(open val node: Node) {

    /**
     * The timestamp of this [DebugEntry] as an [Instant]
     */
    val timestamp: Instant = Instant.now()

    /**
     * The timestamp as formatted label. The pattern for formatting is "HH:mm:ss.SSS". Returns the
     * label as a [String].
     */
    val timestampLabel: String
        get() = TIME_FORMATTER.format(timestamp)

    companion object {
        private val TIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS", Locale.ROOT).withZone(ZoneId.systemDefault())
    }
}

/**
 * [OnActionEntry] is a [DebugEntry] which is received when an [DebugActionInput] has received
 * a value.
 */
data class OnActionEntry(val input: DebugActionInput<*>, val value: Any) : DebugEntry(input.node)

/**
 * [OnException] is a [DebugEntry] which is received when an [Exception] has occurred.
 */
data class OnException(override val node: Node, val exception: Exception) : DebugEntry(node)

/**
 * [OnValueReceivedEntry] is a [DebugEntry] which is received when an [DebugInput] has received
 * a value.
 */
data class OnValueReceivedEntry(val input: DebugInput<*>, val value: Any) : DebugEntry(input.node)

/**
 * [OnValueTransmittedEntry] is a [DebugEntry] which is received when an [DebugOutput] has
 * transmitted a value.
 */
data class OnValueTransmittedEntry(val output: DebugOutput<*>, val value: Any, val receiver: Rx<*>) : DebugEntry(output.node)