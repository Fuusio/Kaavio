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

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.debug.node.BooleanProbe
import org.fuusio.kaavio.node.stream.BooleanInjector

import org.junit.Assert.*
import org.junit.Test

internal class XorTest : KaavioTest() {

    @Test
    fun `Test all inputs true`() {
        // Given
        val node = Xor()
        val injectors = Array(4) { BooleanInjector() }
        val probe = BooleanProbe()
        injectors.forEach { injector -> injector.output connect node.input }
        node.output connect probe

        // When
        injectors.forEach { injector -> injector.inject(true) }

        // Then
        assertTrue(probe.hasValue())
        probe.assertHasValue(false)
    }

    @Test
    fun `Test all inputs false`() {
        // Given
        val node = Xor()
        val injectors = Array(4) { BooleanInjector() }
        val probe = BooleanProbe()
        injectors.forEach { injector -> injector.output connect node.input }
        node.output connect probe

        // When
        injectors.forEach { injector -> injector.inject(false) }

        // Then
        assertTrue(probe.hasValue())
        probe.assertHasValue(false)
    }

    @Test
    fun `Test one of inputs false`() {
        // Given
        val node = Xor()
        val injectors = Array(4) { BooleanInjector() }
        val probe = BooleanProbe()
        injectors.forEach { injector -> injector.output connect node.input }
        node.output connect probe

        // When
        injectors.forEachIndexed { index, injector ->
            val value = when(index) {
                            2 -> false
                            else -> true
                        }
            injector.inject(value)
        }

        // Then
        assertTrue(probe.hasValue())
        probe.assertHasValue(false)
    }

    @Test
    fun `Test one of inputs true`() {
        // Given
        val node = Xor()
        val injectors = Array(4) { BooleanInjector() }
        val probe = BooleanProbe()
        injectors.forEach { injector -> injector.output connect node.input }
        node.output connect probe

        // When
        injectors.forEachIndexed { index, injector ->
            val value = when(index) {
                2 -> true
                else -> false
            }
            injector.inject(value)
        }

        // Then
        assertTrue(probe.hasValue())
        probe.assertHasValue(true)
    }
}