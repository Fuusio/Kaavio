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