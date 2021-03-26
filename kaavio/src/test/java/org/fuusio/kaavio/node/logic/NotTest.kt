package org.fuusio.kaavio.node.logic

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.Input

import org.junit.Assert.*
import org.junit.Test

internal class NotTest : KaavioTest() {

    @Test
    fun `Test input is true`() {
        // Given
        val node = Not()
        val output = Output<Boolean>()
        val receiver = Input<Boolean>(mock())
        output connect node.input
        node.output connect receiver

        // When
        output.transmit(true)

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(false, receiver.value)
    }

    @Test
    fun `Test input is false`() {
        // Given
        val node = Not()
        val output = Output<Boolean>()
        val receiver = Input<Boolean>(mock())
        output connect node.input
        node.output connect receiver

        // When
        output.transmit(false)

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(true, receiver.value)
    }
}