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

import org.fuusio.kaavio.node.base.SingleInputSingleOutputNode
import org.fuusio.kaavio.StatefulNode

/**
 * [Var] is a [StatefulNode] that can store an immutable value of specified type. It can be used to
 * represent a variable in a graph implementation.
 */
open class Var<I : Any>(initialValue: I? = null) :
    SingleInputSingleOutputNode<I, I>(), StatefulNode<I> {
    private var _value: I? = initialValue

    override val value: I?
        get() = _value

    override fun onFired() {
        _value = input.value
        output.transmit(_value!!)
    }

    override fun hasValue(): Boolean = _value != null
}

class AnyVar(initialValue: Any? = null) : Var<Any>(initialValue)

class BooleanVar(initialValue: Boolean? = null) : Var<Boolean>(initialValue)

class ByteVar(initialValue: Byte? = null) : Var<Byte>(initialValue)

class CharVar(initialValue: Char? = null) : Var<Char>(initialValue)

class DoubleVar(initialValue: Double? = null) : Var<Double>(initialValue)

class FloatVar(initialValue: Float? = null) : Var<Float>(initialValue)

class IntVar(initialValue: Int? = null) : Var<Int>(initialValue)

class LongVar(initialValue: Long? = null) : Var<Long>(initialValue)

class ShortVar(initialValue: Short? = null): Var<Short>(initialValue)

class StringVar(initialValue: String? = null) : Var<String>(initialValue)