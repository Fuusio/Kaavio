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
 * [Val] is a [StatefulNode] that stores an immutable value of specified type. It can be used to
 * represent a constant in a graph implementation.
 */
open class Val<I :Any>(override val value: I) : SingleInputSingleOutputNode<I,I>(), StatefulNode<I> {

    override fun onFired() {
        output.transmit(value)
    }

    override fun hasValue(): Boolean = true
}

class AnyVal(value: Any) : Val<Any>(value)

class BooleanVal(value: Boolean) : Val<Boolean>(value)

class ByteVal(value: Byte) : Val<Byte>(value)

class CharVal(value: Char) : Val<Char>(value)

class DoubleVal(value: Double) : Val<Double>(value)

class FloatVal(value: Float) : Val<Float>(value)

class IntVal(value: Int) : Val<Int>(value)

class LongVal(value: Long) : Val<Long>(value)

class ShortVal(value: Short): Val<Short>(value)

class StringVal(value: String) : Val<String>(value)