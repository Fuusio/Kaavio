package org.fuusio.kaavio.node.comparison

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.Input

import org.junit.Assert.*
import org.junit.Test

internal class EqualsTest : KaavioTest() {

    @Test
    fun `Test single input should throw exception`() {
        // Given
        val node = Equals<String>()
        val outputs = Array(1) { Output<String>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        try {
            outputs.forEach { output -> output.transmit("foo") }
            fail("Should have thrown exception")
        } catch (e: Exception) {
            // Succeeded
        }

        // Then
    }

    @Test
    fun `Test all inputs equal Strings`() {
        // Given
        val node = Equals<String>()
        val outputs = Array(4) { Output<String>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        outputs.forEach { output -> output.transmit("foo") }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(true, receiver.value)
    }

    @Test
    fun `Test all inputs equal Ints`() {
        // Given
        val node = Equals<Int>()
        val outputs = Array(4) { Output<Int>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        outputs.forEach { output -> output.transmit(42) }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(true, receiver.value)
    }

    @Test
    fun `Test one of inputs not equal`() {
        // Given
        val node = Equals<String>()
        val outputs = Array(4) { Output<String>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        outputs.forEachIndexed { index, output ->
            val value = when(index) {
                            2 -> "bar"
                            else -> "foo"
                        }
            output.transmit(value)
        }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(false, receiver.value)
    }
}