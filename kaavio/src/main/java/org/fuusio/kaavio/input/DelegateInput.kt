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
package org.fuusio.kaavio.input

import org.fuusio.kaavio.*

/**
 * A [DelegateInput] delegates function invocations to the given [input].
 */
open class DelegateInput<I : Any>(val input: Input<I>, node: Node) : Input<I>(node) {

    override fun cacheValue(ctx: Ctx, value: I) {
        input.cacheValue(ctx, value)
    }

    override fun onReceive(ctx: Ctx, value: I) {
        input.onReceive(ctx, value)
    }

    override fun connect(transmitter: Tx<I>): Rx<I> {
        return input.connect(transmitter)
    }

    override fun connect(transmitters: List<Tx<I>>) {
        input.connect(transmitters)
    }

    override fun connect(vararg transmitters: Tx<I>) {
        input.connect(*transmitters)
    }

    override fun reset(ctx: Ctx) {
        super.reset(ctx)
        input.reset(ctx)
    }

    override fun hasValue(ctx: Ctx) = input.hasValue(ctx)
}