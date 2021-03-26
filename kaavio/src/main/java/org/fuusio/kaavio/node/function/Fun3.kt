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

import org.fuusio.kaavio.SingleOutputNode

class Fun3<I1 :Any,I2 :Any,I3 :Any,O :Any>(name: String? = null, val function: (I1, I2, I3) -> O)
    : SingleOutputNode<O>(name) {
    val input1 = inputOf<I1>(this)
    val input2 = inputOf<I2>(this)
    val input3 = inputOf<I3>(this)

    override fun onFired() {
        output.transmit(function(input1.value, input2.value, input3.value))
    }
}