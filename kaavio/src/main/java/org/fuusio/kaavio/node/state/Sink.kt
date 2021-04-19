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
package org.fuusio.kaavio.node.stream

import org.fuusio.kaavio.node.base.SingleInputNode
import org.fuusio.kaavio.StatefulNode

/**
 * [Sink] is a [org.fuusio.kaavio.Node] that has only an input of specified type [I]. [Sink] is
 * capable of receiving a value, but it does not transmit the received value. An instance of [Sink]
 * represents a terminal node in a [org.fuusio.kaavio.graph.Graph]. A [Sink] stores only the latest
 * value it has received.
 */
open class Sink<I : Any> : SingleInputNode<I>(), StatefulNode<I> {
    private var _value: I? = null

    override val value: I?
        get() = _value

    override fun onFired() {
        _value = input.value
    }

    override fun hasValue(): Boolean = _value != null
}

class AnySink : Sink<Any>()

class BooleanSink : Sink<Boolean>()

class ByteSink : Sink<Byte>()

class CharSink : Sink<Char>()

class DoubleSink : Sink<Double>()

class FloatSink : Sink<Float>()

class IntSink : Sink<Int>()

class LongSink : Sink<Long>()

class ShortSink : Sink<Short>()

class StringSink : Sink<String>()