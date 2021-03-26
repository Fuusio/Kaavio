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
import org.fuusio.kaavio.node.stream.Injector
import org.fuusio.kaavio.node.stream.Sink
import org.junit.Assert
import org.junit.Test

class IfTest : KaavioTest() {

    @Test
    fun `Test if function evaluating to true`() {
        // Given
        val `if` = If {int: Int -> int > 10}
        val injector = Injector<Int>()
        val trueSink = Sink<Unit>()
        val falseSink = Sink<Unit>()

        injector.output connect `if`.input
        `if`.onTrue connect trueSink.input
        `if`.onFalse connect falseSink.input

        // When
        injector.inject(42)

        // Then
        Assert.assertTrue(trueSink.hasValue())
        Assert.assertFalse(falseSink.hasValue())
    }

    @Test
    fun `Test if function evaluating to false`() {
        // Given
        val `if` = If {int: Int -> int > 10}
        val injector = Injector<Int>()
        val trueSink = Sink<Unit>()
        val falseSink = Sink<Unit>()

        injector.output connect `if`.input
        `if`.onTrue connect trueSink.input
        `if`.onFalse connect falseSink.input

        // When
        injector.inject(9)

        // Then
        Assert.assertFalse(trueSink.hasValue())
        Assert.assertTrue(falseSink.hasValue())
    }
}