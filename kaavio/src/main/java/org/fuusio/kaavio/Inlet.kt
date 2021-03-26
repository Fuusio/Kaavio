/*
 * Copyright (C) 2016 - 2021 Marko Salmela
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
 * [Inlet] implements a mechanism that makes it possible to dynamically attach any number of
 * transmitters [Tx] to a receiver [Node]. Each transmitter is bound to a dedicated instance of
 * [Input].
 */
class Inlet<I :Any>(private val node: Node) : Rx<I> {

    private val inputs: MutableList<Input<I>> = mutableListOf()

    val values: List<I>
        get() = List(inputs.size) { i -> inputs[i].value }

    override infix fun connect(transmitter: Tx<I>): Rx<I> {
        val input = Input<I>(node)
        inputs.add(input)
        return input connect transmitter
    }

    infix fun connect(transmitters: List<Tx<I>>) {
        transmitters.forEach { transmitter -> this connect transmitter }
    }

    fun connect(vararg transmitters: Tx<I>) {
        transmitters.forEach { transmitter -> this connect transmitter }
    }

    override fun onReceive(value: I) {}
}

fun <T : Any> txs(vararg transmitters: Tx<T>): List<Tx<T>> {
    val list = mutableListOf<Tx<T>>()
    transmitters.forEach { transmitter -> list.add(transmitter) }
    return list
}