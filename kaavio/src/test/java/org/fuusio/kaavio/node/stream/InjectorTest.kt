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
package org.fuusio.kaavio.node.stream

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.controlflow.Trigger
import org.fuusio.kaavio.node.debug.IntInjector
import org.fuusio.kaavio.node.state.IntSink
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InjectorTest : KaavioTest() {

    @Test
    fun `Test injecting an Int`() {
        // Given
        val injector = IntInjector()
        val sink = IntSink()

        injector.output connect sink.input

        // When
        injector.inject(42)

        // Then
        assertTrue(sink.hasValue())
        assertEquals(42, sink.value)
    }

    @Test
    fun `Test injecting an Int using a Trigger`() {
        // Given
        val injector = IntInjector()
        val sink = IntSink()
        val trigger = Trigger()

        trigger.output connect injector.input
        injector.output connect sink.input
        injector.value = 42

        // When
        trigger.fire()

        // Then
        assertTrue(sink.hasValue())
        assertEquals(42, sink.value)
    }
}