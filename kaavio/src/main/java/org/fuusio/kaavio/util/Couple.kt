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
package org.fuusio.kaavio.util

/**
 * [Couple] is a [Tuple] of two values.
 */
data class Couple<T1, T2>(
    val first: T1,
    val second: T2,
) : Tuple {
    override val size: Int = 2

    override operator fun get(index: Int): Any? =
        when (index) {
            0 -> first
            1 -> second
            else -> throw IndexOutOfBoundsException()
        }
}