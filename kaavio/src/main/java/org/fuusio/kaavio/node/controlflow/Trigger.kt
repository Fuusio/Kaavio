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
package org.fuusio.kaavio.node.controlflow

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.node.base.SingleInputSingleOutputNode

/**
 * [Trigger] is a [org.fuusio.kaavio.Node] that can be programmatically triggered to transmit
 * a [Unit] to a connected receiving [org.fuusio.kaavio.Node].
 */
class Trigger : SingleInputSingleOutputNode<Unit, Unit>() {

    fun fire() {
        onFired(Ctx()) // TODO
    }

    override fun onFired(ctx: Ctx) {
        output.transmit(ctx, Unit)
    }
}