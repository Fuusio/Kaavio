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
 * [Tx] is an interface for transmitter objects i.e. objects that transmit values of type [O].
 *
 * [Tx] objects can be connected to transmitting [Rx] objects of the same type.
 */
interface Tx<O : Any> {

    /**
     * Request this [Tx] to transmit the latest cached value if any using the given [Ctx].
     */
    fun transmit(ctx: Ctx)

    /**
     * Connects the given [receiver] object given as [Rx] object to this transmitter.
     */
    infix fun connect(receiver: Rx<O>)
}