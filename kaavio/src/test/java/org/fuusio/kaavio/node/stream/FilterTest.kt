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
package org.fuusio.kaavio.node.stream

import org.fuusio.kaavio.KaavioTest

import org.junit.Assert.*
import org.junit.Test

class FilterTest : KaavioTest() {

    @Test
    fun `Test filtering of lower case chars`() {
        // Given
        val filter = Filter { char: Char -> char.isLowerCase() }
        val injector = CharInjector()
        val buffer = Buffer<Char>()

        injector.output connect filter.input
        filter.output connect buffer.input

        // When
        val string = "foBoAR"
        string.forEach { char -> injector.inject(char) }

        // Then
        assertTrue(buffer.isNotEmpty())
        val chars = buffer.get(true).toCharArray()
        assertTrue(buffer.isEmpty())
        assertEquals("foo", String(chars))
    }
}