/*
 * Copyright (C) 2019 - 2022 Marko Salmela
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

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Given Output")
internal class OutputTest : KaavioTest() {

    // Test subject
    private val output = Output<Int>(mockk())
    private val ctx = Ctx()

    @DisplayName("When transmitting Int value 42")
    @Nested
    inner class TransmitCases {

        private val input = mockInput<Int>()

        @BeforeEach
        fun beforeCase() {
            ctx.clear()
            output connect input
            output.transmit(ctx,42)
        }

        @Test
        @DisplayName("Then connected Input should have a value")
        fun case1() {
            assertTrue(input.hasValue(ctx))
        }

        @Test
        @DisplayName("Then should have cached Int value 42")
        fun case2() {
            assertEquals(42, input.get(ctx))
        }
    }

    @DisplayName("When connecting to an Input")
    @Nested
    inner class ConnectCases {

        private val input = mockInput<Int>()

        @BeforeEach
        fun beforeCase() {
            output connect input
        }

        @Test
        @DisplayName("Then Input.connect should have been invoked")
        fun case1() {
            verify { input.connect(output) }
        }

        @Test
        @DisplayName("Then Output should have the Input as a receiver")
        fun case2() {
            assertTrue(output.hasReceiver(input))
        }
    }

    @DisplayName("When checking if transmitted values are to be cached")
    @Nested
    inner class IsValueCachedCases {

        @BeforeEach
        fun beforeCase() {
            // Do nothing
        }

        @Test
        @DisplayName("Then by default false should be returned")
        fun case1() {
            assertFalse(output.isValueCached())
        }
    }
}