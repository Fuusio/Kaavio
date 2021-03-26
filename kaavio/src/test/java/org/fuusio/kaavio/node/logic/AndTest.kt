package org.fuusio.kaavio.node.logic

import org.fuusio.kaavio.*

import org.junit.Assert.*
import org.junit.Test

internal class AndTest : KaavioTest() {

    @Test
    fun `Test 4 inputs true`() {
        // Given
        val node = And()
        val output1 = Output<Boolean>()
        val output2 = Output<Boolean>()
        val output3 = Output<Boolean>()
        val output4 = Output<Boolean>()
        val outputs = arrayOf(output1, output2, output3, output4)

        val receiver = Input<Boolean>(mock())

        //node.input .rx (output1, output2, output3, output4)
        node.input connect txs(output1, output2, output3, output4)
        node.output connect receiver

        // When
        outputs.forEach { output -> output.transmit(true) }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(true, receiver.value)
    }

    @Test
    fun `Test all inputs true`() {
        // Given
        val node = And()
        val outputs = Array(4) { Output<Boolean>() }
        val receiver = Input<Boolean>(mock())
        outputs.forEach { output -> output connect node.input }
        node.output connect receiver

        // When
        outputs.forEach { output -> output.transmit(true) }

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(true, receiver.value)
    }

    @Test
    fun `Test all inputs false`() {
        // Given
        val node = And()
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
        val node = And()
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
}