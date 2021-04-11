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
package org.fuusio.kaavio

import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("Given Input")
internal class InputTest : KaavioTest() {

    private val node = mockNode()

    // Test subject
    val input = Input<Int>(node)

    @DisplayName("When receiving Int value 42")
    @Nested
    inner class OnReceiveCases {

        @BeforeEach
        fun beforeCase() {
            input.onReceive(42)
        }

        @Test
        @DisplayName("Then should have a value")
        fun case1() {
            assertTrue(input.hasValue())
        }

        @Test
        @DisplayName("Then INput should have cached Int value 42")
        fun case2() {
            assertEquals(42, input.value)
        }

        @Test
        @DisplayName("Then should have invoked Node.onInputReceived")
        fun case3() {
            verify { node.onInputValueReceived(input) }
        }
    }

    @DisplayName("When resetting a cached value")
    @Nested
    inner class ResetCases {

        @BeforeEach
        fun beforeCase() {
            input.onReceive(42)
            input.reset()
        }

        @Test
        @DisplayName("Then Input should not have a value")
        fun case1() {
            assertTrue(!input.hasValue())
        }
    }

    @DisplayName("When connecting to an Output")
    @Nested
    inner class ConnectCases {

        private val output = mockOutput<Int>()

        @BeforeEach
        fun beforeCase() {
            input connect output
        }

        @Test
        @DisplayName("Then Output should have the Input as a receiver")
        fun case1() {
            verify { output.addReceiver(input) }
        }
    }
}