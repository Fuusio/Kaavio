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
package org.fuusio.kaavio.node.logic

import org.fuusio.kaavio.*
import org.fuusio.kaavio.KaavioTest

import org.junit.Assert.*
import org.junit.Test

internal class AndTest : KaavioTest() {

    @Test
    fun `Test 4 inputs true`() {
        // Given
        val node = And()
        val output1 = Output<Boolean>(mock())
        val output2 = Output<Boolean>(mock())
        val output3 = Output<Boolean>(mock())
        val output4 = Output<Boolean>(mock())
        val outputs = arrayOf(output1, output2, output3, output4)

        val receiver = Input<Boolean>(mock())

        //node.input .rx (output1, output2, output3, output4)
        node.input connect outputs(output1, output2, output3, output4)
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
        val outputs = Array(4) { Output<Boolean>(mock()) }
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
        val outputs = Array(4) { Output<Boolean>(mock()) }
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
        val outputs = Array(4) { Output<Boolean>(mock()) }
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