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
package org.fuusio.kaavio.factory

import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.output.DebugOutput
import org.fuusio.kaavio.output.DelegateOutput

/**
 * [DefaultOutputFactory] provides a default implementation of [OutputFactory].
 */
class DefaultOutputFactory : OutputFactory {

    override fun <O :Any> createDebugOutput(node: Node) = DebugOutput<O>(node)

    override fun <O : Any> createDelegateOutput(output: Output<O>, node: Node) =
        DelegateOutput(output, node)

    override fun <O :Any> createOutput(node: Node) = Output<O>(node)
}