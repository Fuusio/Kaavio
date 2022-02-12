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
package org.fuusio.kaavio.output

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.debugger.GraphDebugger
import org.fuusio.kaavio.node.debug.Probe
import org.fuusio.kaavio.node.debug.Probes

/**
 * [DebugOutput] implements an [Output] type which can be used for debugging the transmitted values
 * by notifying [GraphDebugger] when ever a value is transmitted via this [DebugOutput].
 */
class DebugOutput<O: Any>(node: Node, var name: String? = null) : Output<O>(node) {

    override fun transmit(ctx: Ctx) {
        value?.let { receivers.forEach { receiver ->
            GraphDebugger.onValueTransmitted(this, it, receiver)
            receiver.onReceive(ctx, it) }
        }
    }

    /**
     * Connects this [DebugOutput] to a [Probe] provided by invoking function [Probes.connect]
     * for the given [probes]. Returns a [Probe] that is added to set of receivers i.e. [Rx] objects
     * of this [DebugOutput].
     */
    infix fun connect(probes: Probes): Probe<O> {
        val probe = probes.connect(this)
        addReceiver(probe)
        return probe
    }

    /**
     * Reconnects this [DebugOutput] to given [receiver]. Reconnecting can be used for debugging purposes
     * to replace actual [Node]s from a [org.fuusio.kaavio.graph.Graph] e.g. with mock nodes.
     */
    fun reconnect(receiver: Rx<O>) {
        receivers.clear()
        receivers.add(receiver)
    }
}