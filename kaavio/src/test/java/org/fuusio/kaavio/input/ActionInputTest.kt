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
package org.fuusio.kaavio.input

import io.mockk.verify
import org.fuusio.kaavio.KaavioTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("Given ActionInput")
internal class ActionInputTest : KaavioTest() {

    private var flag = false

    // Test subject
    private val actionInput = ActionInput<Int>(mockNode()) { int -> flag = int > 10 }

    @DisplayName("When receiving Int value 42")
    @Nested
    inner class OnReceiveCases {

        @BeforeEach
        fun beforeCase() {
            actionInput.onReceive(42)
        }

        @Test
        @DisplayName("Then the action should have set the flag to true")
        fun case1() {
            assertTrue(flag)
        }
    }

    @DisplayName("When connecting to an Output")
    @Nested
    inner class ConnectCases {

        private val output = mockOutput<Int>()

        @BeforeEach
        fun beforeCase() {
            actionInput connect output
        }

        @Test
        @DisplayName("Then Output should have the ActionInput as a receiver")
        fun case1() {
            verify { output.addReceiver(actionInput) }
        }
    }
}