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
package org.fuusio.kaavio.node.coroutines

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.fuusio.kaavio.SingleInputSingleOutputNode
import org.fuusio.kaavio.StatefulNode
import org.fuusio.kaavio.coroutines.DispatcherType

/**
 * [AsyncLiveData] TODO
 */
class AsyncLiveData<I : Any, O : Any>(
    private val dispatcherType: DispatcherType = DispatcherType.DEFAULT,
    private val liveDataFunction: suspend (I) -> O,
) : SingleInputSingleOutputNode<I, O>(), StatefulNode<O> {

    private val data = MutableLiveData<O>()

    override val state: O?
        get() = data.value

    fun hasValue(): Boolean = data.value != null

    fun observe(owner: LifecycleOwner, observer: Observer<O>) {
        data.observe(owner, observer)
    }

    fun observer(owner: () -> Lifecycle, observer: (O) -> Unit) {
        data.observe(owner, observer)
    }

    fun removeObserver(observer: Observer<O>) {
        data.removeObserver(observer)
    }

    fun removeObserver(owner: LifecycleOwner) {
        data.removeObservers(owner)
    }

    fun hasActiveObservers(): Boolean = data.hasActiveObservers()

    fun hasObservers(): Boolean = data.hasObservers()

    override fun onFired() {
        context.coroutineScope.launch {
            withContext(context.dispatcher(dispatcherType)) {
                val value = liveDataFunction(input.value)
                withContext(context.mainDispatcher) {
                    data.value = value
                    output.transmit(value)
                }
            }
        }
    }
}