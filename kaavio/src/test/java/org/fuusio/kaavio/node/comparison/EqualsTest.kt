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
package org.fuusio.kaavio.node.comparison

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.BooleanProbe
import org.fuusio.kaavio.node.stream.IntInjector
import org.fuusio.kaavio.node.stream.StringInjector
import org.junit.jupiter.api.*

@DisplayName("Given Equals")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EqualsTest : KaavioTest() {

    @DisplayName("When Equals is connected to only single output and a value is injected")
    @Nested
    inner class Cases1 {

        private val injectors = Array(1) { StringInjector() }

        // Test subject
        private val node = Equals<String>()

        @BeforeEach
        fun beforeEachCase() {
            injectors.forEach { injector -> injector.output connect node.input }
        }

        @Test
        @DisplayName("Then Equals should thrown an exception")
        fun case1() {
            // When
            try {
                injectors.forEach { injector -> injector.inject("foo") }
                fail("Should have thrown exception")
            } catch (e: Exception) {
                // Succeeded
            }
        }
    }

    @DisplayName("When Equals receives multiple equal strings")
    @Nested
    inner class Cases2 {

        private val injectors = Array(5) { StringInjector() }
        private val probe = BooleanProbe()

        // Test subject
        private val node = Equals<String>()

        @BeforeEach
        fun beforeEachCase() {
            injectors.forEach { injector -> injector.output connect node.input }
            node.output connect probe
            injectors.forEach { injector -> injector.inject("foo") }
        }

        @Test
        @DisplayName("Then Equals should output true")
        fun case1() {
            probe.assertHasValue(true)
        }
    }

    @DisplayName("When Equals receives multiple equal Int values")
    @Nested
    inner class Cases3 {

        private val injectors = Array(5) { IntInjector() }
        private val probe = BooleanProbe()

        // Test subject
        private val node = Equals<Int>()

        @BeforeEach
        fun beforeEachCase() {
            injectors.forEach { injector -> injector.output connect node.input }
            node.output connect probe
            injectors.forEach { injector -> injector.inject(42) }
        }

        @Test
        @DisplayName("Then Equals should output true")
        fun case1() {
            probe.assertHasValue(true)
        }
    }

    @DisplayName("When Equals receives strings with one string being not equal")
    @Nested
    inner class Cases4 {

        private val injectors = Array(5) { StringInjector() }
        private val probe = BooleanProbe()

        // Test subject
        private val node = Equals<String>()

        @BeforeEach
        fun beforeEachCase() {
            injectors.forEach { injector -> injector.output connect node.input }
            node.output connect probe
            injectors.forEachIndexed { index, injector ->
                injector.inject(when (index) {
                    3 -> "bar"
                    else -> "foo"
                })
            }
        }

        @Test
        @DisplayName("Then Equals should output false")
        fun case1() {
            probe.assertHasValue(false)
        }
    }
}