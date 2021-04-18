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

import androidx.annotation.CallSuper

/**
 * [Input] is an object that implement [Rx] to receive values for the owner [node]. Inputs are typed.
 * When an input receives a value it invokes [Node.onInputValueReceived] for the owner node.
 */
@Suppress("LeakingThis")
open class Input<I :Any>(val node: Node) : Rx<I> {
    private var _value: I? = null
    private var statefulNode: StatefulNode<I>? = null

    /**
     * The received value (if any). See [hasValue].
     */
    internal val value: I
        get() = _value!!

    init {
        node.attachInput(this)
    }

    /**
     * Invoked for this [Input] to receive the given [value]. By default the received is cached and
     * the owned [Node] is notified about the received value by invoking [Node.onInputValueReceived].
     */
    override fun onReceive(value: I) {
        cacheValue(value)
        node.onInputValueReceived(this)
    }

    /**
     * Cache the given value to internal cache.
     */
    internal open fun cacheValue(value: I) {
        _value = value
    }

    /**
     * Resets this [Input] by removing the received value.
     */
    @CallSuper
    open fun reset() {
        _value = null
    }

    override infix fun connect(transmitter: Tx<I>): Rx<I> =
        when (transmitter) {
            is Output -> {
                transmitter.addReceiver(this)
                val outputNode = transmitter.node
                if (outputNode is StatefulNode<*>) {
                    @Suppress("UNCHECKED_CAST")
                    statefulNode = outputNode as StatefulNode<I>
                }
                this
            }
            else -> this
        }

    /**
     * Connects the [transmitters], given as [List] of [Tx]s, to this [Input].
     */
    open infix fun connect(transmitters: List<Tx<I>>) {
        transmitters.forEach { transmitter -> this connect transmitter }
    }

    /**
     * Connects the [transmitters], given as variable length list of [Tx]s, to this [Input].
     */
    open fun connect(vararg transmitters: Tx<I>) {
        transmitters.forEach { transmitter -> this connect transmitter }
    }

    /**
     * Tests if this [Input] has received a value.
     */
    open fun hasValue(): Boolean =
        if (_value != null) {
            true
        } else {
            val node = statefulNode
            if (node != null) {
                _value = node.value
                node.hasValue()
            } else {
                false
            }
        }
}