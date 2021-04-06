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
package org.fuusio.kaavio.node.state

import org.fuusio.kaavio.SingleInputSingleOutputNode
import org.fuusio.kaavio.StatefulNode

/**
 * [Var] TODO
 */
open class Var<I :Any> : SingleInputSingleOutputNode<I,I>(), StatefulNode<I> {
    private var _value: I? = null

    override val state: I?
        get() = value

    val value: I?
        get() = _value

    override fun onFired() {
        _value = input.value
        output.transmit(_value!!)
    }

    fun hasValue(): Boolean = _value != null
}

class AnyVar : Var<Any>()

class BooleanVar : Var<Boolean>()

class ByteVar : Var<Byte>()

class CharVar : Var<Char>()

class DoubleVar : Var<Double>()

class FloatVar : Var<Float>()

class IntVar : Var<Int>()

class LongVar : Var<Long>()

class ShortVar : Var<Short>()

class StringVar : Var<String>()