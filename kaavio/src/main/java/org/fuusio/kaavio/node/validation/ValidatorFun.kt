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
package org.fuusio.kaavio.node.validation

import org.fuusio.kaavio.SingleInputSingleOutputNode

/**
 *  [ValidatorFun] is a node that uses the given [function] to validate the received input.
 */
class ValidatorFun<I :Any>(name: String? = null, val function: (I) -> Boolean)
    : SingleInputSingleOutputNode<I,Boolean>(name) {

    override fun onFired() {
        output.transmit(function(input.value))
    }
}