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
package org.fuusio.kaavio

/**
 * [Ctx] TODO
 */
@Suppress("UNCHECKED_CAST")
data class Ctx(val values: MutableMap<Int, Any> = mutableMapOf()) {

    /**
     * Checks if there is value for the given [key].
     */
    fun has(key: Int): Boolean = values.containsKey(key)

    /**
     * Gets the value for the given [key].
     */
    fun <T: Any> get(key: Int): T = values[key]!! as T

    /**
     * Sets the given [value] for the given [key].
     */
    fun set(key: Int, value: Any) {
        values[key] = value
    }

    /**
     * Clear all the values.
     */
    fun clear() {
        values.clear()
    }

    /**
     * Clear the value for the given [key].
     */
    fun clear(key: Int) {
        values.remove(key)
    }
}
