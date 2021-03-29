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
package org.fuusio.kaavio

@Suppress("LeakingThis")
open class Input<I :Any>(val node: Node) : Rx<I> {
    private var _value: I? = null

    val value: I
        get() = _value!!

    init {
        node.attachInput(this)
    }

    override fun onReceive(value: I) {
        storeValue(value)
        node.onInputValueReceived(this)
    }

    open fun storeValue(value: I) {
        _value = value
    }

    open fun reset() {
        _value = null
    }

    override infix fun connect(transmitter: Tx<I>): Rx<I> =
        when (transmitter) {
            is Output -> {
                transmitter.addReceiver(this)
                this
            }
            else -> this
        }

    infix fun connect(transmitters: List<Tx<I>>) {
        transmitters.forEach { transmitter -> this connect transmitter }
    }

    fun connect(vararg transmitters: Tx<I>) {
        transmitters.forEach { transmitter -> this connect transmitter }
    }


    open fun hasValue(): Boolean = _value != null
}