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

class BooleanSwitchTest : KaavioTest() {

    @Test
    fun `Test inject true to BooleanSwitch`() {
        // Given
        val switch = BooleanSwitch()
        val injector = Injector<Boolean>()
        val trueSink = Sink<Unit>()
        val falseSink = Sink<Unit>()

        injector.output connect switch.input
        switch.onTrue connect trueSink.input
        switch.onFalse connect falseSink.input

        // When
        injector.inject(true)

        // Then
        Assert.assertFalse(falseSink.hasValue())
        Assert.assertTrue(trueSink.hasValue())
    }

    @Test
    fun `Test inject false to BooleanSwitch`() {
        // Given
        val switch = BooleanSwitch()
        val injector = Injector<Boolean>()
        val trueSink = Sink<Unit>()
        val falseSink = Sink<Unit>()

        injector.output connect switch.input
        switch.onTrue connect trueSink.input
        switch.onFalse connect falseSink.input

        // When
        injector.inject(false)

        // Then
        Assert.assertTrue(falseSink.hasValue())
        Assert.assertFalse(trueSink.hasValue())
    }
}