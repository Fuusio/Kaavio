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
package org.fuusio.kaavio.node.function

import org.fuusio.kaavio.*
import org.fuusio.kaavio.testbench.TwoInputsNodeTestBench
import org.fuusio.kaavio.testbench.toValueOption

internal class Fun2Test : TwoInputsNodeTestBench<String, String, String>() {

    override fun testCases() = cases(
        inputValues("Hello ", "World!") to "Hello World!".toValueOption()
    )

    override fun node(injector1: Tx<String>, injector2: Tx<String>, observer: Rx<String>) =
        Fun2 { string1: String, string2: String -> string1 + string2 }.apply {
            injector1 connect arg1
            injector2 connect arg2
            output connect observer
        }
}