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
package org.fuusio.kaavio.debugger

import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.IntInjector
import org.fuusio.kaavio.node.state.IntSink

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("Given an IntInjector which is connected to an IntSink in debug mode")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GraphDebuggerTest : KaavioTest() {

    // Test Subjects
    private lateinit var injector: IntInjector
    private lateinit var sink: IntSink

    @BeforeAll
    fun beforeAllCases() {
        Kaavio.isDebugMode = true
        injector = IntInjector()
        sink = IntSink()
        injector.output connect sink.input
    }

    @DisplayName("When injecting Int value 42 via IntInjector")
    @Nested
    inner class InjectCases {

        @BeforeEach
        fun beforeEachCase() {
            GraphDebugger.clearDebugEntries()
            injector.inject(42)
        }

        @AfterEach
        fun afterEachCase() {
            GraphDebugger.clearDebugEntries()
        }

        @Test
        @DisplayName("Then the IntSink should have a value 42")
        fun case1() {
            assertTrue(sink.hasValue())
            assertEquals(42, sink.value)
        }

        @Test
        @DisplayName("Then GraphDebugger should have two debug entries: OnValueTransmittedEntry and OnValueReceivedEntry")
        fun case2() {
            val entries = GraphDebugger.debugEntries
            assertEquals(2, entries.size)
            val entry1 = entries[0]
            val entry2 = entries[1]
            assertTrue(entry1 is OnValueTransmittedEntry)
            assertTrue(entry2 is OnValueReceivedEntry)
            entry1 as OnValueTransmittedEntry
            assertEquals(injector.output, entry1.output)
        }
    }

    @DisplayName("When invoking GraphDebugger.clearDebugEntries")
    @Nested
    inner class ClearDebugEntriesCases {

        @BeforeEach
        fun beforeEachCase() {
            injector.inject(42)
            GraphDebugger.clearDebugEntries()
        }

        @Test
        @DisplayName("Then GraphDebugger should not have any debug entries")
        fun case1() {
            assertTrue(GraphDebugger.debugEntries.isEmpty())
        }
    }
}