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
package org.fuusio.kaavio.node.controlflow

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.Injector
import org.fuusio.kaavio.node.state.Sink
import org.junit.Assert
import org.junit.Test
import kotlin.Unit as Unit1

class EnumSwitchTest : KaavioTest() {

    @Test
    fun `Test select Kotlin`() {
        // Given
        val switch = LanguageSwitch()
        val injector = Injector<Language>()
        val sinks = mutableMapOf(
                Language.DART to Sink(),
                Language.JAVA to Sink(),
                Language.KOTLIN to Sink(),
                Language.SWIFT to Sink<Unit1>(),
        )

        injector.output connect switch.input
        switch.dart connect sinks[Language.DART]!!.input
        switch.java connect sinks[Language.JAVA]!!.input
        switch.kotlin connect sinks[Language.KOTLIN]!!.input
        switch.swift connect sinks[Language.SWIFT]!!.input

        // When
        injector.inject(Language.KOTLIN)

        // Then
        Assert.assertFalse(sinks[Language.DART]!!.hasValue())
        Assert.assertFalse(sinks[Language.JAVA]!!.hasValue())
        Assert.assertTrue(sinks[Language.KOTLIN]!!.hasValue())
        Assert.assertFalse(sinks[Language.SWIFT]!!.hasValue())
    }

    enum class Language {
        DART,
        JAVA,
        KOTLIN,
        SWIFT,
    }

    class LanguageSwitch : EnumSwitch<Language>() {
        val dart = caseOutput(Language.DART)
        val java = caseOutput(Language.JAVA)
        val kotlin = caseOutput(Language.KOTLIN)
        val swift = caseOutput(Language.SWIFT)
    }
}