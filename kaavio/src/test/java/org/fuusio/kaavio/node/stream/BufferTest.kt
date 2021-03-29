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

import org.fuusio.kaavio.KaavioTest

import org.junit.Assert.*
import org.junit.Test

class BufferTest : KaavioTest() {

    @Test
    fun `Test buffering and flush`() {
        // Given
        val injector = CharInjector()
        val buffer = Buffer<Char>()
        val sink = Sink<List<Char>>()
        injector.output connect buffer.input
        buffer.output connect sink.input

        // When
        val string = "0123456789"
        string.forEach { char -> injector.inject(char) }

        // Then
        assertTrue(buffer.isNotEmpty())
        assertEquals(10, buffer.size())
        assertFalse(sink.hasValue())

        // When
        buffer.flush()

        // Then
        assertTrue(buffer.isEmpty())
        assertTrue(sink.hasValue())
        assertEquals("0123456789", String(sink.value!!.toCharArray()))
    }

    @Test
    fun `Test capacity`() {
        // Given
        val injector = CharInjector()
        val buffer = Buffer<Char>(10)
        val sink = Sink<List<Char>>()
        injector.output connect buffer.input
        buffer.output connect sink.input

        // When
        val string = "012345678901234"
        string.forEach { char -> injector.inject(char) }

        // Then
        assertTrue(buffer.isNotEmpty())
        assertEquals(5, buffer.size())
        assertTrue(sink.hasValue())
        assertTrue(sink.hasValue())
        assertEquals("0123456789", String(sink.value!!.toCharArray()))

        // When
        buffer.flush()

        // Then
        assertTrue(buffer.isEmpty())
        assertTrue(sink.hasValue())
        assertEquals("01234", String(sink.value!!.toCharArray()))
    }
}