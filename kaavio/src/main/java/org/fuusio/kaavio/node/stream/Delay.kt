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
package org.fuusio.kaavio.node.stream

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fuusio.kaavio.SingleInputSingleOutputNode
import java.util.concurrent.TimeUnit

/**
 * [Delay] waits for the specified [delay] before the transmitting the received value via output.
 */
class Delay<I : Any>(
    private val delay: Long,
    private val timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
) : SingleInputSingleOutputNode<I, I>() {

    override fun onFired() {
        val value = input.value
        GlobalScope.launch {
            delay(timeUnit.toMillis(delay))
            output.transmit(value)
        }
    }
}
