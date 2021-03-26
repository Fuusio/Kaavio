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
package org.fuusio.kaavio.node.comparison

import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.Inlet

class Equals<I :Any>(name: String? = null) : Node(name) {
    val input: Inlet<I> = Inlet(this)
    val output = Output<Boolean>()

    override fun onFired() {
        val values = input.values

        if (values.size < 2) {
            throw IllegalStateException("Equals requires at least two inputs.")
        }
        var previous: I? = null
        values.forEach { value ->
            if (previous != null && value != previous) {
                output.transmit(false)
                return
            }
            previous = value
        }
        output.transmit(true)
    }
}