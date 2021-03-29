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
package org.fuusio.kaavio.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

object DefaultCoroutinesConfig : CoroutinesConfig {

    override val graphCoroutineScope: CoroutineScope = GlobalScope

    override val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    override val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    override val unconfinedDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
}