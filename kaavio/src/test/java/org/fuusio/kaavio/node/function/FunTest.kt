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
import kotlin.math.PI
import kotlin.math.floor

import org.junit.Assert.*
import org.junit.Test

class FunTest : KaavioTest() {

    @Test
    fun `Test String reverse function`() {
        // Given
        val node = Fun { string: String -> string.reversed() }
        val output = Output<String>()
        val receiver = Input<String>(mock())
        output connect node.arg
        node.output connect receiver

        // When
        output.transmit("foo")

        // Then
        assertTrue(receiver.hasValue())
        assertEquals("oof", receiver.value)
    }

    @Test
    fun `Test Double floor conversion to Long function`() {
        // Given
        val node = Fun { double: Double -> floor(double).toLong() }
        val output = Output<Double>()
        val receiver = Input<Long>(mock())
        output connect node.arg
        node.output connect receiver

        // When
        output.transmit(PI)

        // Then
        assertTrue(receiver.hasValue())
        assertEquals(3L, receiver.value)
    }
}