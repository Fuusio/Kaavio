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
package org.fuusio.kaavio.node.validation

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.BooleanObserver
import org.junit.jupiter.api.*

@DisplayName("Given EmailValidator")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EmailValidatorGwtTest : KaavioTest() {

    // Test subject
    private val node = EmailValidator()
    private val probe = BooleanObserver()
    private val ctx = Ctx()

    @BeforeEach
    fun beforeEachCase() {
        ctx.clear()
    }

    @BeforeAll
    fun beforeAllCases() {
        node.output connect probe
    }

    @DisplayName("When receiving valid email: foo.bar@baz.com")
    @Nested
    inner class InputCases1 {

        @BeforeEach
        fun beforeEachCase() {
            node.input.onReceive(ctx,"foo.bar@baz.com")
        }

        @Test
        @DisplayName("Then EmailValidator should output true")
        fun case1() {
            probe.assertHasValue(true)
        }
    }

    @DisplayName("When receiving valid email: foo.bar(at))baz.com")
    @Nested
    inner class InputCases2 {

        @BeforeEach
        fun beforeEachCase() {
            node.input.onReceive(ctx,"foo.bar(at))baz.com")
        }

        @Test
        @DisplayName("Then EmailValidator should output false")
        fun case1() {
            probe.assertHasValue(false)
        }
    }
}