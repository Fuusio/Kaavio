package org.fuusio.kaavio.node.function

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.Input

import org.junit.Assert.*
import org.junit.Test

class Fun2Test : KaavioTest() {

    @Test
    fun `Test String concatenation function`() {
        // Given
        val node = Fun2 { string1: String, string2: String -> string1 + string2 }
        val output1 = Output<String>()
        val output2 = Output<String>()
        val receiver = Input<String>(mock())

        output1 connect node.input1
        output2 connect node.input2
        node.output connect receiver

        // When
        output1.transmit("foo")
        output2.transmit("bar")

        // Then
        assertTrue(receiver.hasValue())
        assertEquals("foobar", receiver.value)
    }

    @Test
    fun `Test multiplication of two Ints`() {
        // Given
        val node = Fun2 { int1: Int, int2: Int -> int1 * int2 }
        val output1 = Output<Int>()
        val output2 = Output<Int>()
        val receiver = Input<Int>(mock())
        output1 connect node.input1
        output2 connect node.input2
        node.output connect receiver

        // When
        output1.transmit(21)
        output2.transmit(2)

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(42, receiver.value)
    }
}