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
package org.fuusio.kaavio.graph

import org.fuusio.kaavio.node.comparison.Equals
import org.fuusio.kaavio.node.logic.And
import org.fuusio.kaavio.node.state.StringVar
import org.fuusio.kaavio.node.state.Var
import org.fuusio.kaavio.node.stream.BooleanSink
import org.fuusio.kaavio.node.stream.Sink
import org.fuusio.kaavio.node.validation.EmailValidator
import org.fuusio.kaavio.node.validation.ValidatorFun

data class SignUpGraph(
    val userName: StringVar = StringVar(),
    val userNameValidator: ValidatorFun<String> = ValidatorFun { string: String -> string.length > 6 },
    val email: StringVar = StringVar(),
    val emailConfirm: StringVar = StringVar(),
    val emailValidator: EmailValidator = EmailValidator(),
    val emailsEquals: Equals<String> = Equals(),
    val and: And = And(),
    val result: BooleanSink = BooleanSink()
) : AbstractGraph() {

    override fun onConnectNodes() {
        userName.output connect userNameValidator.input

        email.output connect emailValidator.input

        emailsEquals.input connect email.output
        emailsEquals.input connect emailConfirm.output

        and.input connect userNameValidator.output
        and.input connect emailValidator.output
        and.input connect emailsEquals.output
        and.output connect result.input
    }
}
