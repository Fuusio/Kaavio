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
package org.fuusio.kaavio.node.function

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.node.base.SingleOutputNode

/**
 * [Fun2] TODO
 */
class Fun2<I1 : Any, I2 : Any, O : Any>(val function: (I1, I2) -> O) : SingleOutputNode<O>() {
    val arg1 = inputOf<I1>()
    val arg2 = inputOf<I2>()

    override fun onFired(ctx: Ctx) {
        output.transmit(ctx, function(arg1.get(ctx), arg2.get(ctx)))
    }
}