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

import org.fuusio.kaavio.node.base.SingleInputSingleOutputNode

/**
 * [Injector] is a [org.fuusio.kaavio.Node] that has an output of specified type. [Injector]
 * is capable of transmitting a given value. An [Injector] stores the latest given value so it can
 * be transmitter when ever the [Injector] is triggered to transmit the value.
 */
open class Injector<O :Any> : SingleInputSingleOutputNode<Unit, O>() {

    var value: O? = null

    /**
     * Sets the current value of this [Injector] to be the given [value] and injects it.
     */
    fun inject(value: O) {
        this.value = value
        onFired()
    }

    override fun onFired() = transmit(value!!)
}

class AnyInjector : Injector<Any>()

class BooleanInjector : Injector<Boolean>()

class ByteInjector : Injector<Byte>()

class CharInjector : Injector<Char>()

class DoubleInjector : Injector<Double>()

class FloatInjector : Injector<Float>()

class IntInjector : Injector<Int>()

class LongInjector : Injector<Long>()

class ShortInjector : Injector<Short>()

class StringInjector : Injector<String>()
