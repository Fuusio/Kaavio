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
package org.fuusio.kaavio.node.string

import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.Tx
import org.fuusio.kaavio.testbench.SingleInputNodeTestBench
import org.fuusio.kaavio.testbench.toValueOption

internal class LengthTest : SingleInputNodeTestBench<String, Int>() {

    override fun testCases() = mapOf(
        "foo" to "foo".length.toValueOption(),
        "foobar" to "foobar".length.toValueOption(),
        "Hello world!" to "Hello world!".length.toValueOption(),
    )

    override fun node(injector: Tx<String>, probe: Rx<Int>) =
        Length().apply {
            injector connect input
            output connect probe
        }
}