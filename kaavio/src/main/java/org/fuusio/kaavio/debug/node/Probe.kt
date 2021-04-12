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
package org.fuusio.kaavio.debug.node

import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.SingleInputNode
import org.fuusio.kaavio.Tx
import org.fuusio.kaavio.graph.Graph
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.output.DebugOutput

/**
 * [Probe] is a testing and debugging utility [org.fuusio.kaavio.Node] that has only a single input
 * of specified type. [Probe] is capable of receiving values, but it does not transmit the received
 * value. A [Probe] stores all the values it receives.
 */
open class Probe<I :Any>(var output: DebugOutput<I>? = null) : SingleInputNode<I>(), Rx<I> {
    private val _values = mutableListOf<I>()

    /**
     * All the values as a [List] received by this [Probe] since last invocation of [reset].
     */
    val values: List<I>
        get() = _values

    /**
     * The latest value received by this [Probe] or [null if no values have received since last
     * invocation of [reset].
     */
    val latestValue: I?
        get() = if (_values.isNotEmpty()) _values.last() else null

    override var name: String
        get() = when (output) {
            null -> "Probe"
            else -> "Probe(${Graph.getNodeName(output!!.node)})"
        }
        set(value) { super.name = value }

    override fun onFired() {
        _values.add(input.value)
    }

    /**
     * Test if this [Probe] has received a value.
     */
    fun hasValue(): Boolean = _values.isNotEmpty()

    /**
     * Test if this [Probe] has received the given [value] as a latest value.
     */
    fun hasValue(value: Any): Boolean = hasValue() && latestValue == value

    override fun onReceive(value: I) {
        input.onReceive(value)
    }

    override fun connect(transmitter: Tx<I>): Rx<I> =
        input.connect(transmitter)

    /**
     * Resets this [Probe], e.g., by clearing the latest value.
     */
    fun reset() {
        _values.clear()

        if (input is DebugInput) {
            input.reset()
        }
    }
}

class AnyProbe(output: DebugOutput<Any>? = null) : Probe<Any>(output)

class BooleanProbe(output: DebugOutput<Boolean>? = null) : Probe<Boolean>(output)

class ByteProbe(output: DebugOutput<Byte>? = null) : Probe<Byte>(output)

class CharProbe(output: DebugOutput<Char>? = null) : Probe<Char>(output)

class DoubleProbe(output: DebugOutput<Double>? = null) : Probe<Double>(output)

class FloatProbe(output: DebugOutput<Float>? = null) : Probe<Float>(output)

class IntProbe(output: DebugOutput<Int>? = null) : Probe<Int>(output)

class LongProbe(output: DebugOutput<Long>? = null) : Probe<Long>(output)

class ShortProbe(output: DebugOutput<Short>? = null) : Probe<Short>(output)

class StringProbe(output: DebugOutput<String>? = null) : Probe<String>(output)