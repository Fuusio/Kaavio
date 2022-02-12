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

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.Injector
import org.fuusio.kaavio.node.state.Sink

import org.junit.Assert.*
import org.junit.Test

internal class ValidatorTest : KaavioTest() {

    class FooValidator : Validator<String, Boolean>() {
        override fun validate(value: String): Boolean = value == "foo"
    }

    @Test
    fun `Test valid input`() {
        // Given
        val validator = FooValidator()
        val injector = Injector<String>()
        val sink = Sink<Boolean>()

        injector.output connect validator.input
        validator.output connect sink.input

        // When
        injector.inject("foo")

        // Then
        assertTrue(sink.hasValue())
        assertEquals(true, sink.value)
    }

    @Test
    fun `Test invalid input`() {
        // Given
        val validator = FooValidator()
        val injector = Injector<String>()
        val sink = Sink<Boolean>()

        injector.output connect validator.input
        validator.output connect sink.input

        // When
        injector.inject("bar")

        // Then
        assertTrue(sink.hasValue())
        assertEquals(false, sink.value)
    }

}