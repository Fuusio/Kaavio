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

/**
 * [Output] acts as a port to dispatch or transmit the given value to connected receiving [Node]s.
 * An output is typed.
 */
open class Output<O :Any> : Tx<O> {
    protected var value: O? = null
    protected var receivers: MutableSet<Rx<O>> = mutableSetOf()

    /**
     * Transmits the given [value] to connected, receiving [Rx] objects. The value is optionally
     * cached (see [Output.isValueCached].
     */
    internal open fun transmit(value: O) {
        this.value = value
        transmit()
        if (!isValueCached()) { this.value = null }
    }

    /**
     * Transmits the cached [value], if any, to connected, receiving [Rx] objects.
     */
    override fun transmit() {
        value?.let { receivers.forEach { receiver -> receiver.onReceive(it) } }
    }

    /**
     * Connects the [receiver] given as a [Rx] object to receive values from this [Output].
     */
    override infix fun connect(receiver: Rx<O>) {
        receivers.add(receiver connect this)
    }

    /**
     * Adds the [receiver] given as a [Rx] object to receive values from this [Output].
     */
    internal fun addReceiver(receiver: Rx<O>) {
        receivers.add(receiver)
    }

    /**
     * Specifies if the transmitted value is cached for this [Output].
     */
    open fun isValueCached(): Boolean = false

    /**
     * Tests if this [Output] has the given [receiver] as connected receiver.
     */
    fun hasReceiver(receiver: Rx<Int>): Boolean = receivers.contains(receiver)
}