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
import org.fuusio.kaavio.node.function.Fun2
import org.fuusio.kaavio.node.function.Fun3
import org.fuusio.kaavio.node.logic.And
import org.fuusio.kaavio.node.state.StringVar
import org.fuusio.kaavio.node.stream.BooleanSink
import org.fuusio.kaavio.node.validation.EmailValidator
import org.fuusio.kaavio.node.validation.ValidatorFun

data class SignUpGraph(
    val userName: StringVar = StringVar(),
    val userNameValidator: ValidatorFun<String> = ValidatorFun { string -> string.length > 6 },
    val email: StringVar = StringVar(),
    val emailConfirm: StringVar = StringVar(),
    val emailValidator: EmailValidator = EmailValidator(),
    val emailsEquals: Equals<String> = Equals(),
    val password: StringVar = StringVar(),
    val passwordConfirm: StringVar = StringVar(),
    val passwordsEquals: Equals<String> = Equals(),
    val passwordValidator: ValidatorFun<String> = ValidatorFun { string -> string.length >= 8 },
    val and: And = And(),
    val result: BooleanSink = BooleanSink(),
    val loginInfo: Fun3<String, String, String, LoginInfo> =
        Fun3 { email, password, userName -> LoginInfo(email = email, password = password, userName = userName) },
) : AbstractGraph() {

    override fun onConnectNodes() {
        userName.output connect userNameValidator.input

        email.output connect emailValidator.input

        emailsEquals.input connect email.output
        emailsEquals.input connect emailConfirm.output

        and.input connect userNameValidator.output
        and.input connect emailValidator.output
        and.input connect emailsEquals.output
        and.input connect passwordsEquals.output
        and.input connect passwordValidator.output
        and.output connect result.input

        loginInfo.arg1 connect email.output
        loginInfo.arg2 connect password.output
        loginInfo.arg3 connect userName.output
    }
}
