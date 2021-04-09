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
package org.fuusio.kaavio

import androidx.annotation.CallSuper
import org.fuusio.kaavio.graph.GraphContext

/**
 * [AbstractNode] provides an abstract base class for concrete implementations of [Node]s.
 */
abstract class AbstractNode : Node {
    private var _context: GraphContext? = null
    private var _name: String? = null

    override val context: GraphContext
        get() = _context!!

    override var name: String
        get() = _name ?: this::class.simpleName!!
        set(value) { _name = value }

    private val inputs: MutableList<Input<*>> = mutableListOf()

    @CallSuper
    override fun attachInput(input: Input<*>) {
        inputs.add(input)
    }

    @CallSuper
    override fun onInit(context: GraphContext) {
        _context = context
    }

    @CallSuper
    override fun onInputValueReceived(input: Input<*>) {
        inputs.forEach {if (!it.hasValue()) return }
        onFired()
    }

    protected fun <I :Any> actionInputOf(action: (I) -> Unit): Input<I> = Kaavio.actionInput(this, action)

    protected fun <I :Any> inletOf(): Inlet<I> = Inlet(this)

    protected fun <I :Any> inputOf(): Input<I> = Kaavio.input(this)

    protected fun <O :Any> outputOf(): Output<O> = Kaavio.output(this)
}