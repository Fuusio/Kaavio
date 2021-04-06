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
package org.fuusio.kaavio.node.logic

import org.fuusio.kaavio.SingleOutputNode

/**
 * [And] implements logical and function which takes any number of input values and outputs
 * [Boolean] true when all the input values are true when the node is fired. Otherwise
 * the node outputs false.
 */
class And : SingleOutputNode<Boolean>() {
    val input = inletOf<Boolean>()

    override fun onFired() {
        input.values.forEach { value ->
            if (!value) {
                output.transmit(false)
                return
            }
        }
        output.transmit(true)
    }
}