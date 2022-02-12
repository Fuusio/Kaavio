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

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.fuusio.kaavio.input.ActionInput
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.Injector
import org.fuusio.kaavio.node.state.IntSink
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Test

class TimerTest : KaavioTest() {

    @Test
    fun `Test timer`() {
        // Given
        var start = 0L
        val timer = Timer(1000)
        val trigger = Trigger()
        val actionInput = ActionInput<Unit>(mock()) { _, _ ->
            val duration = System.currentTimeMillis() - start
            if (duration < 1000) fail()
        }
        val injector = Injector<Int>()
        val sink = IntSink()

        injector.value = 42

        trigger.output connect timer.input
        timer.output connect actionInput
        timer.output connect injector.input
        injector.output connect sink.input

        // When
        start = System.currentTimeMillis()
        trigger.fire()

        // Then
        runBlocking {
            delay(2000L)
        }
        Assert.assertTrue(sink.hasValue())
        Assert.assertEquals(42, sink.value)
    }
}