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

import org.fuusio.kaavio.factory.DefaultInputFactory
import org.fuusio.kaavio.factory.DefaultOutputFactory
import org.fuusio.kaavio.factory.InputFactory
import org.fuusio.kaavio.factory.OutputFactory
import org.fuusio.kaavio.coroutines.CoroutinesConfig
import org.fuusio.kaavio.coroutines.DefaultCoroutinesConfig
import org.fuusio.kaavio.input.DelegateInput
import org.fuusio.kaavio.output.DelegateOutput
import java.io.PrintStream

/**
 * [Kaavio] is a singleton configuration object for all kaavio [org.fuusio.kaavio.graph.Graph]
 * instances.
 */
object Kaavio {
    /**
     * The [CoroutinesConfig] applied for injecting coroutines related objects such as coroutine
     * scopes and dispatchers.
     */
    var coroutinesConfig: CoroutinesConfig = DefaultCoroutinesConfig

    /**
     * A [Boolean] flag specifying if [org.fuusio.kaavio.graph.Graph]s are executed in debug mode.
     */
    var isDebugMode: Boolean = false

    /**
     * The [InputFactory] used for creating [Input] and [ActionInput] instances when corresponding
     * functions [input] or [actionInput] are invoked.
     */
    var inputFactory: InputFactory = DefaultInputFactory()

    /**
     * The out [PrintStream] which is used, for instance, by [org.fuusio.kaavio.debug.node.Print]
     * node.
     */
    var out: PrintStream = System.out
    /**
     * The [OutputFactory] used for creating [Output] instances when function [output] is invoked.
     */
    var outputFactory: OutputFactory = DefaultOutputFactory()

    /**
     * Creates a new instance of an [ActionInput] for the given [Node] and [action] function.
     */
    fun <I: Any> actionInput(node: Node, action: (I) -> Unit) =
        if (isDebugMode) inputFactory.createDebugActionInput(node, action)
        else inputFactory.createActionInput(node, action)

    /**
     * Creates a new instance of an [DelegateInput] for the given actual [input] and [Node].
     */
    fun <I: Any> delegateInput(input: Input<I>, node: Node): DelegateInput<I> =
        inputFactory.createDelegateInput(input, node)

    /**
     * Creates a new instance of an [DelegateOutput] for the given actual [output] and [Node].
     */
    fun <O: Any> delegateOutput(output: Output<O> ,node: Node): DelegateOutput<O> =
        outputFactory.createDelegateOutput(output, node)

    /**
     * Creates a new instance of an [Input] for the given [Node].
     */
    fun <I: Any> input(node: Node): Input<I> =
        if (isDebugMode) inputFactory.createDebugInput(node)
        else inputFactory.createInput(node)

    /**
     * Creates a new instance of an [Output] for the given [Node].
     */
    fun <O: Any> output(node: Node): Output<O> =
        if (isDebugMode) outputFactory.createDebugOutput(node)
        else outputFactory.createOutput(node)
}
