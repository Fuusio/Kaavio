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

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.node.base.SingleInputNode

class When<I: Any>(caseValues: List<I>) : SingleInputNode<I>() {

    private val caseOutputs = mutableMapOf<I, Output<Unit>>()

    var elseOutput =  outputOf<Unit>()

    init {
        caseValues.forEach { value ->
            caseOutputs[value] = createCaseOutput(value)
        }
    }

    /**
     * Gets a case [Output] for the given [value]. Returns an [Output].
     */
    fun caseOutput(value: I): Output<Unit> = caseOutputs[value]!!

    private fun createCaseOutput(value: I): Output<Unit> {
        val output = outputOf<Unit>()
        caseOutputs[value] = output
        return output
    }

    override fun onFired(ctx: Ctx) {
        val output =  caseOutputs[input.get(ctx)] ?: elseOutput
        output.transmit(ctx, Unit)
    }
}
