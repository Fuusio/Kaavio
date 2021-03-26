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

open class Var<I :Any>(name: String? = null) : SingleInputSingleOutputNode<I,I>(name), StatefulNode<I> {
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

class AnyVar(name: String? = null) : Var<Any>(name)

class BooleanVar(name: String? = null) : Var<Boolean>(name)

class ByteVar(name: String? = null) : Var<Byte>(name)

class CharVar(name: String? = null) : Var<Char>(name)

class DoubleVar(name: String? = null) : Var<Double>(name)

class FloatVar(name: String? = null) : Var<Float>(name)

class IntVar(name: String? = null) : Var<Int>(name)

class LongVar(name: String? = null) : Var<Long>(name)

class ShortVar(name: String? = null) : Var<Short>(name)

class StringVar(name: String? = null) : Var<String>(name)