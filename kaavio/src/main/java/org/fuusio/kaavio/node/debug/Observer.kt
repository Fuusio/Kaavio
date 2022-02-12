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
package org.fuusio.kaavio.node.debug

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.node.base.SingleInputNode
import org.fuusio.kaavio.Tx
import org.fuusio.kaavio.Graph
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.output.DebugOutput

/**
 * [Observer] is a testing and debugging utility [org.fuusio.kaavio.Node] that has only a single input
 * of specified type. [Observer] is capable of receiving values, but it does not transmit the received
 * value. A [Observer] stores all the values it receives.
 */
open class Observer<I :Any>(var output: DebugOutput<I>? = null) : SingleInputNode<I>(), Rx<I> {
    private val _values = mutableListOf<I>()

    /**
     * All the values as a [List] received by this [Observer] since last invocation of [reset].
     */
    val values: List<I>
        get() = _values

    /**
     * The latest value received by this [Observer] or [null if no values have received since last
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

    override fun onFired(ctx: Ctx) {
        _values.add(input.get(ctx))
    }

    /**
     * Test if this [Observer] has received a value.
     */
    fun hasValue(): Boolean = _values.isNotEmpty()

    /**
     * Test if this [Observer] has received the given [value] as a latest value.
     */
    fun hasValue(value: Any): Boolean = hasValue() && latestValue == value

    override fun onReceive(ctx: Ctx, value: I) {
        input.onReceive(ctx, value)
    }

    override fun connect(transmitter: Tx<I>): Rx<I> =
        input.connect(transmitter)

    /**
     * Resets this [Observer], e.g., by clearing the latest value.
     */
    fun reset(ctx: Ctx) {
        _values.clear()

        if (input is DebugInput) {
            input.reset(ctx)
        }
    }
}

class AnyObserver(output: DebugOutput<Any>? = null) : Observer<Any>(output)

class BooleanObserver(output: DebugOutput<Boolean>? = null) : Observer<Boolean>(output)

class ByteObserver(output: DebugOutput<Byte>? = null) : Observer<Byte>(output)

class CharObserver(output: DebugOutput<Char>? = null) : Observer<Char>(output)

class DoubleObserver(output: DebugOutput<Double>? = null) : Observer<Double>(output)

class FloatObserver(output: DebugOutput<Float>? = null) : Observer<Float>(output)

class IntObserver(output: DebugOutput<Int>? = null) : Observer<Int>(output)

class LongObserver(output: DebugOutput<Long>? = null) : Observer<Long>(output)

class ShortObserver(output: DebugOutput<Short>? = null) : Observer<Short>(output)

class StringObserver(output: DebugOutput<String>? = null) : Observer<String>(output)