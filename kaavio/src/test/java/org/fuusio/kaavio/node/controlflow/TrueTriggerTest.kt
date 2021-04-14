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
package org.fuusio.kaavio.node.controlflow

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.debug.node.Probe
import org.junit.jupiter.api.*

@DisplayName("Given TrueTrigger")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TrueTriggerTest : KaavioTest() {

    @DisplayName("When receiving true")
    @Nested
    inner class TrueCases {

        // Test subject
        private val node = TrueTrigger()

        private val probe = Probe<Unit>()

        @BeforeEach
        fun beforeEachCase() {
            node.output connect probe
            node.input.onReceive(true)
        }

        @Test
        @DisplayName("Then TrueTrigger should output Unit")
        fun case1() {
            probe.assertHasValue(Unit)
        }
    }

    @DisplayName("When receiving false")
    @Nested
    inner class FalseCase {

        // Test subject
        private val node = TrueTrigger()

        private val probe = Probe<Unit>()

        @BeforeEach
        fun beforeEachCase() {
            node.output connect probe
            node.input.onReceive(false)
        }

        @Test
        @DisplayName("Then TrueTrigger should not output Unit")
        fun case1() {
            probe.assertHasNoValue()
        }
    }
}