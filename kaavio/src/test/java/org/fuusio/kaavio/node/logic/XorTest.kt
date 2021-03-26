package org.fuusio.kaavio.node.logic

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.Input

import org.junit.Assert.*
import org.junit.Test

internal class XorTest : KaavioTest() {

    @Test
    fun `Test all inputs true`() {
        // Given
        val node = Xor()
        val outputs = Array(4) { Output<Boolean>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        outputs.forEach { output -> output.transmit(true) }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(false, receiver.value)
    }

    @Test
    fun `Test all inputs false`() {
        // Given
        val node = Xor()
        val outputs = Array(4) { Output<Boolean>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        receiver connect node.output

        // When
        outputs.forEach { output -> output.transmit(false) }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(false, receiver.value)
    }

    @Test
    fun `Test one of inputs false`() {
        // Given
        val node = Xor()
        val outputs = Array(4) { Output<Boolean>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        outputs.forEachIndexed { index, output ->
            val value = when(index) {
                            2 -> false
                            else -> true
                        }
            output.transmit(value)
        }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(false, receiver.value)
    }

    @Test
    fun `Test one of inputs true`() {
        // Given
        val node = Xor()
        val outputs = Array(4) { Output<Boolean>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        outputs.forEachIndexed { index, output ->
            val value = when(index) {
                2 -> true
                else -> false
            }
            output.transmit(value)
        }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(true, receiver.value)
    }
}