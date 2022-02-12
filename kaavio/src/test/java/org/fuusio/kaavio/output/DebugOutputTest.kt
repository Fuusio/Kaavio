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

import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.debugger.GraphDebugger
import org.fuusio.kaavio.debugger.OnValueReceivedEntry
import org.fuusio.kaavio.debugger.OnValueTransmittedEntry
import org.fuusio.kaavio.node.debug.IntInjector
import org.fuusio.kaavio.node.state.IntSink
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DebugOutputTest : KaavioTest() {

    @Before
    fun before() {
        Kaavio.isDebugMode = true
        GraphDebugger.clearDebugEntries()
    }

    @After
    fun after() {
        Kaavio.isDebugMode = false
        GraphDebugger.clearDebugEntries()
    }

    @Test
    fun `Test transmitting a value`() {
        // Given
        val injector = IntInjector()
        val sink = IntSink()

        injector.output connect sink.input

        // When
        injector.inject(42)

        // Then
        assertTrue(sink.hasValue())
        assertEquals(42, sink.value)
        val entries = GraphDebugger.debugEntries
        assertEquals(2, entries.size)
        val entry = entries[0]
        assertTrue(entry is OnValueTransmittedEntry)
        assertTrue(entries[1] is OnValueReceivedEntry)
        entry as OnValueTransmittedEntry
        assertEquals(injector.output, entry.output)
    }
}