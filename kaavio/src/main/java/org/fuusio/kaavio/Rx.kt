/*
 * Copyright (C) 2019 - 2022 Marko Salmela
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
 * [Rx] is an interface for receiver objects i.e. objects that receive values of type [I].
 *
 * [Rx] objects can be connected to transmitting [Tx] objects of the same type.
 */
interface Rx<I : Any> {

    /**
     * Invoked to this receiver [Rx] to receive a [value] transmitter by connected transmitter [Tx].
     */
    fun onReceive(ctx: Ctx, value: I)

    /**
     * Connects the given [transmitter] object to this or some other receiver object returned
     * as [Rx].
     */
    infix fun connect(transmitter: Tx<I>): Rx<I>
}