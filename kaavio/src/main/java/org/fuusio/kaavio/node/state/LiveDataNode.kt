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
package org.fuusio.kaavio.node.state

import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.fuusio.kaavio.node.base.SingleInputSingleOutputNode
import org.fuusio.kaavio.StatefulNode

/**
 * [LiveDataNode] implements a graph node that encapsulates a [MutableLiveData] and provides
 * an API to delegate function invocations, e.g. [MutableLiveData.observe] to it.
 */
class LiveDataNode<I : Any> : SingleInputSingleOutputNode<I, I>(), StatefulNode<I> {

    private val liveData = MutableLiveData<I>()

    override val value: I?
        get() = liveData.value

    override fun hasValue(): Boolean = liveData.value != null

    /**
     * See [MutableLiveData.observe].
     */
    @MainThread
    fun observe(owner: LifecycleOwner, observer: Observer<I>) {
        liveData.observe(owner, observer)
    }

    /**
     * See [MutableLiveData.observe].
     */
    @MainThread
    fun observe(owner: () -> Lifecycle, observer: (I) -> Unit) {
        liveData.observe(owner, observer)
    }

    /**
     * See [MutableLiveData.observeForever].
     */
    @MainThread
    fun observeForever(observer: Observer<I>) {
        liveData.observeForever(observer)
    }

    /**
     * See [MutableLiveData.removeObserver].
     */
    @MainThread
    fun removeObserver(observer: Observer<I>) {
        liveData.removeObserver(observer)
    }

    /**
     * See [MutableLiveData.removeObserver].
     */
    @MainThread
    fun removeObserver(owner: LifecycleOwner) {
        liveData.removeObservers(owner)
    }

    /**
     * See [MutableLiveData.postValue].
     */
    fun postValue(value: I) {
        liveData.postValue(value)
    }

    /**
     * See [MutableLiveData.setValue].
     */
    @MainThread
    fun setValue(value: I) {
        liveData.value = value
    }

    /**
     * See [MutableLiveData.hasActiveObservers]
     */
    fun hasActiveObservers(): Boolean = liveData.hasActiveObservers()

    /**
     * See [MutableLiveData.hasObservers]
     */
    fun hasObservers(): Boolean = liveData.hasObservers()

    override fun onFired() {
        val value = input.value
        liveData.value = value
        output.transmit(value)
    }
}