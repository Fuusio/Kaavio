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

        output1 connect node.arg1
        output2 connect node.arg2
        node.output connect receiver

        // When
        output1.transmit("Hello ")
        output2.transmit("World!")

        // Then
        assertTrue(receiver.hasValue())
        assertEquals("Hello World!", receiver.value)
    }

    @Test
    fun `Test multiplication of two Ints`() {
        // Given
        val node = Fun2 { int1: Int, int2: Int -> int1 + int2 }
        val output1 = Output<Int>()
        val output2 = Output<Int>()
        val receiver = Input<Int>(mock())
        output1 connect node.arg1
        output2 connect node.arg2
        node.output connect receiver

        // When
        output1.transmit(1)
        output2.transmit(10)

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(11, receiver.value)
    }
}