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

internal class RegexFilterTest : KaavioTest() {

    @Test
    fun `Test valid expression`() {
        // Given
        val filter = RegexFilter("a[bc]+d?")
        val injector = Injector<String>()
        val buffer = Buffer<String>()

        injector.output connect filter.input
        filter.output connect buffer.input

        // When
        val strings = arrayOf("abbccd", "abbxcd", "abbbbbbcccc", "xdffs", "abc")
        strings.forEach { string -> injector.inject(string) }

        // Then
        assertEquals(3, buffer.size())
        val filteredStrings = buffer.get(true)
        assertTrue(filteredStrings.contains("abbccd"))
        assertTrue(filteredStrings.contains("abc"))
        assertTrue(filteredStrings.contains("abbbbbbcccc"))
        assertFalse(filteredStrings.contains("abbxcd"))
        assertFalse(filteredStrings.contains("xdffs"))
    }
}