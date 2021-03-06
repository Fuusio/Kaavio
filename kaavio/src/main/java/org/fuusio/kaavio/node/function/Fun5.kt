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
package org.fuusio.kaavio.node.function

import org.fuusio.kaavio.node.base.SingleOutputNode

class Fun5<I1 : Any, I2 : Any, I3 : Any, I4 : Any, I5 : Any, O : Any>(val function: (I1, I2, I3, I4, I5) -> O)
    : SingleOutputNode<O>() {
    val arg1 = inputOf<I1>()
    val arg2 = inputOf<I2>()
    val arg3 = inputOf<I3>()
    val arg4 = inputOf<I4>()
    val arg5 = inputOf<I5>()

    override fun onFired() {
        output.transmit(function(arg1.value, arg2.value, arg3.value, arg4.value, arg5.value))
    }
}