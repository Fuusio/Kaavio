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

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.stream.Buffer
import org.fuusio.kaavio.node.debug.Injector
import org.fuusio.kaavio.node.state.Sink
import org.junit.Assert
import org.junit.Test

class RepeatTriggerTest : KaavioTest() {

    @Test
    fun `Test repeat 10 times`() {
        // Given
        val repeatTrigger = RepeatTrigger(10)
        val trigger = Trigger()
        val injector = Injector<Int>()
        val buffer = Buffer<Int>(10)
        val sink = Sink<List<Int>>()

        injector.value = 42

        trigger.output connect repeatTrigger.input
        repeatTrigger.output connect injector.input
        injector.output connect buffer.input
        buffer.output connect sink.input

        // When
        trigger.fire()

        // Then
        Assert.assertTrue(sink.hasValue())
        Assert.assertTrue(sink.value is List<Int>)
        Assert.assertEquals(10, sink.value?.size)
    }
}