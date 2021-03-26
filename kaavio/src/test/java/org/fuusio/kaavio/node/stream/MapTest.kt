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

class MapTest : KaavioTest() {

    @Test
    fun `Test mapping String to Int`() {
        // Given
        val map = Map { string: String -> string.length }
        val injector = StringInjector()
        val sink = IntSink()

        injector.output connect map.input
        map.output connect sink.input

        // When
        injector.inject(INPUT_STRING)

        // Then
        assertTrue(sink.hasValue())
        assertEquals(INPUT_STRING.length, sink.value)
    }

    companion object {
        const val INPUT_STRING = "foo"
    }
}