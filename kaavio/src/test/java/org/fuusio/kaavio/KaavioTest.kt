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

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.testbench.None
import org.fuusio.kaavio.testbench.Trigger
import org.fuusio.kaavio.util.Quadruple
import org.fuusio.kaavio.util.Quintuple
import org.junit.jupiter.api.fail

abstract class KaavioTest {

    protected fun mock(name: String? = null): MockNode = MockNode(name)

    protected fun mockNode(): Node {
        val node = mockk<Node>()
        every { node.attachInput(any()) } returns Unit
        every { node.onInputValueReceived(any()) } returns Unit
        return node
    }

    protected inline fun <reified I: Any> mockInput(): Input<I> {
        val input = mockk<Input<I>>()
        val value = slot<I>()
        every { input.connect(any<Tx<I>>()) } returns input
        every { input.getProperty("value") } answers { value.captured }
        every { input.onReceive(capture(value)) } returns Unit
        every { input.hasValue() } answers { value.isCaptured }
        return input
    }

    protected fun <O: Any> mockOutput(): Output<O> {
        val output = mockk<Output<O>>()
        every { output.addReceiver(any()) } returns Unit
        every { output.node } returns mockNode()
        return output
    }

    /**
     * Asserts that this [Probe] has received the specified [value].
     */
    fun Probe<*>.assertHasValue(value: Any) {
        if (!hasValue(value)) {
            fail("Probe '$name' has not received value: '$value'")
        }
    }

    /**
     * Asserts that this [Probe] has not transmitted any value to its connected [Probe].
     */
    fun Probe<*>.assertHasNoValue() {
        if (hasValue()) {
            fail("Node '$name' has received value: '$latestValue'")
        }
    }

    fun <T1, T2> pair(first: T1, second: T2): Pair<T1, T2> = Pair(first, second)

    fun <T1, T2, T3> triple(first: T1, second: T2, third: T3): Triple<T1, T2, T3> = Triple(first, second, third)

    fun <T1, T2, T3, T4> quadruple(first: T1, second: T2, third: T3, fourth: T4): Quadruple<T1, T2, T3, T4> = Quadruple(first, second, third, fourth)

    fun <T1, T2, T3, T4, T5> quintuple(first: T1, second: T2, third: T3, fourth: T4, fifth: T5): Quintuple<T1, T2, T3, T4, T5> = Quintuple(first, second, third, fourth, fifth)

    companion object {
        internal fun valueToString(value: Any?): String =
            when (value) {
                null -> "null"
                is String -> "\"$value\""
                is None -> "none"
                is Trigger -> "trigger"
                is Char -> "'$value'"
                else -> value.toString()
            }
    }
}