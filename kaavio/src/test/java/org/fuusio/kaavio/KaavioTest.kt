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
        return output
    }

    companion object {
        const val FOO = "foo"
    }
}