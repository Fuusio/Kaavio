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
import org.fuusio.kaavio.node.function.Fun3
import org.fuusio.kaavio.node.logic.And
import org.fuusio.kaavio.node.state.StringVar
import org.fuusio.kaavio.node.validation.EmailValidator
import org.fuusio.kaavio.node.validation.ValidatorFun

data class SignUpGraph(
    val userName: StringVar = StringVar(),
    val userNameValid: ValidatorFun<String> = ValidatorFun { string -> string.length > 2 },
    val email1: StringVar = StringVar(),
    val email2: StringVar = StringVar(),
    val emailValid: EmailValidator = EmailValidator(),
    val emailsEqual: Equals<String> = Equals(),
    val password1: StringVar = StringVar(),
    val password2: StringVar = StringVar(),
    val passwordsEqual: Equals<String> = Equals(),
    val passwordValid: ValidatorFun<String> = ValidatorFun { string -> string.length >= 8 },
    val allInputsValid: And = And(),
    val loginInfo: Fun3<String, String, String, LoginInfo> =
        Fun3 { email, password, userName -> LoginInfo(email = email, password = password, userName = userName) },
) : AbstractGraph() {

    override fun onConnectNodes() {
        userName.output connect userNameValid.input

        email1.output connect emailValid.input

        emailsEqual.input connect email1.output
        emailsEqual.input connect email2.output

        password1.output connect passwordValid.input

        passwordsEqual.input connect password1.output
        passwordsEqual.input connect password2.output

        allInputsValid.input connect userNameValid.output
        allInputsValid.input connect emailValid.output
        allInputsValid.input connect emailsEqual.output
        allInputsValid.input connect passwordsEqual.output
        allInputsValid.input connect passwordValid.output

        loginInfo.arg1 connect email1.output
        loginInfo.arg2 connect password1.output
        loginInfo.arg3 connect userName.output
    }
}
