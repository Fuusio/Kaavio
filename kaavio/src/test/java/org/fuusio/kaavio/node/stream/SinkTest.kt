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
import org.fuusio.kaavio.node.debug.*
import org.fuusio.kaavio.node.state.*

import org.junit.Assert.*
import org.junit.Test

class SinkTest : KaavioTest() {

    @Test
    fun `Test BooleanSink with a received value`() {
        // Given
        val injector = BooleanInjector()
        val sink = BooleanSink()

        injector.output connect sink.input

        // When
        injector.inject(true)

        // Then
        assertTrue(sink.hasValue())
        assertEquals(true, sink.value)
    }

    @Test
    fun `Test ByteSink with a received value`() {
        // Given
        val injector = ByteInjector()
        val sink = ByteSink()

        injector.output connect sink.input

        // When
        injector.inject(42)

        // Then
        assertTrue(sink.hasValue())
        assertEquals(42.toByte(), sink.value)
    }

    @Test
    fun `Test FloatSink with a received value`() {
        // Given
        val injector = FloatInjector()
        val sink = FloatSink()

        injector.output connect sink.input

        // When
        injector.inject(42f)

        // Then
        assertTrue(sink.hasValue())
        assertEquals(42f, sink.value)
    }

    @Test
    fun `Test IntSink with a received value`() {
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
    fun `Test StringSink with a received value`() {
        // Given
        val injector = StringInjector()
        val sink = StringSink()

        injector.output connect sink.input

        // When
        injector.inject("42")

        // Then
        assertTrue(sink.hasValue())
        assertEquals("42", sink.value)
    }

    @Test
    fun `Test DoubleSink without a received value`() {
        // Given
        val injector = DoubleInjector()
        val sink = DoubleSink()

        injector.output connect sink.input

        // When
        // Do nothing

        // Then
        assertFalse(sink.hasValue())
    }

    @Test
    fun `Test ShortSink with multiple received values`() {
        // Given
        val injector = ShortInjector()
        val sink = ShortSink()

        injector.output connect sink.input

        // When
        val shortValues = arrayOf<Short>(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
        shortValues.forEach { short ->  injector.inject(short) }

        // Then
        assertTrue(sink.hasValue())
        assertEquals(55.toShort(), sink.value)
    }
}