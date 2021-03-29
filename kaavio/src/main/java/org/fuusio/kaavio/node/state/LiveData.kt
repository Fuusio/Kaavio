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

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.fuusio.kaavio.SingleInputSingleOutputNode
import org.fuusio.kaavio.StatefulNode

class LiveData<I : Any>(name: String? = null)
    : SingleInputSingleOutputNode<I,I>(name), StatefulNode<I> {

    private val data = MutableLiveData<I>()

    override val state: I?
        get() = data.value

    fun hasValue(): Boolean = data.value != null

    fun observe(owner: LifecycleOwner, observer: Observer<I>) {
        data.observe(owner, observer)
    }

    fun observer(owner: () -> Lifecycle, observer: (I) -> Unit) {
        data.observe(owner, observer)
    }

    fun observeForever(observer: Observer<I>) {
        data.observeForever(observer)
    }

    fun removeObserver(observer: Observer<I>) {
        data.removeObserver(observer)
    }

    fun removeObserver(owner: LifecycleOwner) {
        data.removeObservers(owner)
    }

    fun postValue(value: I) {
        data.postValue(value)
    }

    fun setValue(value: I) {
        data.value = value
    }

    fun hasActiveObservers(): Boolean = data.hasActiveObservers()

    fun hasObservers(): Boolean = data.hasObservers()

    override fun onFired() {
        val value = input.value
        data.value = value
        output.transmit(value)
    }
}