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
package org.fuusio.kaavio.node.controlflow

import org.fuusio.kaavio.SingleInputSingleOutputNode

/**
 * [IfElse] is a node that uses the given [function] and received input value to select either the
 * [onTrue] or [onFalse] output transmission.
 */
class IfElse<I : Any>(val function: (I) -> Boolean) : SingleInputSingleOutputNode<I, Unit>() {
    val onTrue = outputOf<Unit>()
    val onFalse = outputOf<Unit>()

    override fun onFired() {
        when (function(input.value)) {
            true -> onTrue.transmit(Unit)
            false -> onFalse.transmit(Unit)
        }
    }
}