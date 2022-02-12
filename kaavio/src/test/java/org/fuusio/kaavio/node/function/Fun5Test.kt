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
import org.fuusio.kaavio.testbench.FiveInputsNodeTestBench
import org.fuusio.kaavio.testbench.toValueOption

internal class Fun5Test : FiveInputsNodeTestBench<Int, Int, Int, Int, Int, Int>() {

    override fun testCases() = cases(
        inputValues(1, 20, 300, 4000, 50000)
                to 54321.toValueOption()
    )

    override fun node(injector1: Tx<Int>, injector2: Tx<Int>, injector3: Tx<Int>, injector4: Tx<Int>, injector5: Tx<Int>, observer: Rx<Int>) =
        Fun5 { int1: Int, int2: Int, int3: Int, int4: Int, int5: Int -> int1 + int2 + int3 + int4 + int5 }.apply {
            injector1 connect arg1
            injector2 connect arg2
            injector3 connect arg3
            injector4 connect arg4
            injector5 connect arg5
            output connect observer
        }
}