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
package org.fuusio.kaavio.graph

import androidx.annotation.CallSuper
import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.node.debug.Probe
import org.fuusio.kaavio.node.debug.Probes
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.fail

/**
 * [GraphTest] provides an abstract base class for implementing tests for [Graph]s.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class GraphTest : KaavioTest() {

    @BeforeAll
    @CallSuper
    open fun beforeAllCases() {
        Kaavio.isDebugMode = true
    }

    @AfterAll
    @CallSuper
    open fun afterAllCases() {
        Kaavio.isDebugMode = false
    }

    infix fun <O: Any> Output<O>.connect(probes: Probes): Probe<O> =
        probes.connect(this as DebugOutput<O>)

    /**
     * Asserts that the given [Node] has transmitted the specified value to its connected [Probe].
     */
    fun Probes.assertOutput(node: Node, value: Any) {
        if (!hasOutputValue(node, value)) {
            fail("Node '${node.name}' has not outputted value: '$value'")
        }
    }

    /**
     * Asserts that the given [Node] has not transmitted any value to its connected [Probe].
     */
    fun Probes.assertNoOutput(node: Node) {
        if (!hasNoOutputValue(node)) {
            fail("Node '${node.name}' has an outputted value: '${getValue(node)}'")
        }
    }
}