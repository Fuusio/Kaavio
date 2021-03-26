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
package org.fuusio.kaavio

import org.junit.Assert.*
import org.junit.Test

internal class InputTest : KaavioTest() {

    @Test
    fun `Test onValue function`() {
        // Given
        val input = Input<Int>(mock())

        // When
        input.onReceive(42)

        // Then
        assertEquals(42, input.value)
    }

    @Test
    fun `Test onInputReceived function gets invoked`() {
        // Given
        val node = MockNode()
        val input = Input<Int>(node)

        // When
        input.onReceive(42)

        // Then
        assertTrue(node.onInputReceived)
    }

    @Test
    fun `Test that the received value is stored`() {
        // Given
        val input = Input<Int>(mock())

        // When
        input.onReceive(42)

        // Then
        assertEquals(42, input.value)
    }

    @Test
    fun `Test resetting the stored value`() {
        // Given
        val input = Input<Int>(mock())
        input.onReceive(42)
        assertTrue(input.hasValue())

        // When
        input.reset()

        // Then
        assertFalse(input.hasValue())
    }

    @Test
    fun `Test attaching an output`() {
        // Given
        val input = Input<Int>(mock())
        val output = Output<Int>()
        output connect input
        assertFalse(input.hasValue())

        // When
        output.transmit(42)

        // Then
        assertTrue(input.hasValue())
        assertEquals(42, input.value)
    }

    @Test
    fun `Test hasValue function`() {
        // Given
        val input = Input<Int>(mock())
        assertFalse(input.hasValue())

        // When
        input.onReceive(42)

        // Then
        assertTrue(input.hasValue())
    }
}