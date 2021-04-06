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

/**
 * [RegexValidator] implements a [ValidatorFun] that uses the given regular expression, [pattern], for
 * validating a received input [String]. The regular expression can be also defined as a [String]
 * using the secondary constructor.
 */
open class RegexValidator(private val pattern: Regex) : Validator<String>() {

    constructor(pattern: String) : this(Regex(pattern))

    override fun validate(value: String) = pattern.matches(value)
}
