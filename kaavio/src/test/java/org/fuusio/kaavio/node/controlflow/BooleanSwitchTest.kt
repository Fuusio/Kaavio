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
import org.fuusio.kaavio.node.stream.BooleanInjector
import org.junit.Assert
import org.junit.Test

class BooleanSwitchTest : KaavioTest() {

    @Test
    fun `Test inject true to BooleanSwitch`() {
        // Given
        val switch = BooleanSwitch()
        val injector = BooleanInjector()
        val trueProbe = Probe<Unit>()
        val falseProbe = Probe<Unit>()

        injector.output connect switch.input
        switch.onTrue connect trueProbe
        switch.onFalse connect falseProbe

        // When
        injector.inject(true)

        // Then
        Assert.assertTrue(trueProbe.hasValue())
        Assert.assertFalse(falseProbe.hasValue())
    }

    @Test
    fun `Test inject false to BooleanSwitch`() {
        // Given
        val switch = BooleanSwitch()
        val injector = BooleanInjector()
        val trueProbe = Probe<Unit>()
        val falseProbe = Probe<Unit>()

        injector.output connect switch.input
        switch.onTrue connect trueProbe
        switch.onFalse connect falseProbe

        // When
        injector.inject(false)

        // Then
        Assert.assertFalse(trueProbe.hasValue())
        Assert.assertTrue(falseProbe.hasValue())
    }
}