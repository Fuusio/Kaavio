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
package org.fuusio.kaavio.node.function

import org.fuusio.kaavio.*
import org.fuusio.kaavio.testbench.FourInputsNodeTestBench
import org.fuusio.kaavio.testbench.toValueOption

internal class Fun4Test : FourInputsNodeTestBench<String, String, String, String, String>() {

    override fun testCases() = mapOf(
        quadruple("Kotlin ", "is ", "freaking ", "fun!")
                to "Kotlin is freaking fun!".toValueOption()
    )

    override fun node(injector1: Tx<String>, injector2: Tx<String>, injector3: Tx<String>, injector4: Tx<String>, probe: Rx<String>) =
        Fun4 { string1: String, string2: String, string3: String, string4: String -> string1 + string2 + string3 + string4 }.apply {
            injector1 connect arg1
            injector2 connect arg2
            injector3 connect arg3
            injector4 connect arg4
            output connect probe
        }
}