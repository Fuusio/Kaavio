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
package org.fuusio.kaavio.node.debug

import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.SingleInputNode
import org.fuusio.kaavio.Tx

/**
 * [Print] is a testing and debugging utility [org.fuusio.kaavio.Node] that has only a single input
 * of specified type. [Print] is capable of receiving values, but it does not transmit the received
 * value. A [Print] prints the received input value to [java.io.PrintStream] provided by
 * [Kaavio.out].
 */
class Print<I : Any> : SingleInputNode<I>(), Rx<I> {

    override fun onFired() {
        Kaavio.out.println(input.value.toString())
    }

    override fun onReceive(value: I) {
        input.onReceive(value)
    }

    override fun connect(transmitter: Tx<I>): Rx<I> =
        input.connect(transmitter)
}