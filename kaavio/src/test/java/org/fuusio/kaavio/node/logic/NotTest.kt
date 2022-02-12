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
package org.fuusio.kaavio.node.logic

import org.fuusio.kaavio.*
import org.fuusio.kaavio.testbench.SingleInputNodeTestBench
import org.fuusio.kaavio.testbench.toValueOption

internal class NotTest : SingleInputNodeTestBench<Boolean, Boolean>() {

    override fun testCases() = mapOf(
        true to false.toValueOption(),
        false to true.toValueOption(),
    )

    override fun node(injector: Tx<Boolean>, probe: Rx<Boolean>) =
        Not().apply {
            injector connect input
            output connect probe
        }
}