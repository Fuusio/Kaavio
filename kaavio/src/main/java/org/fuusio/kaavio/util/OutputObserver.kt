package org.fuusio.kaavio.util

import androidx.lifecycle.Observer
import org.fuusio.kaavio.Output

class OutputObserver<T : Any>(private val output: Output<T>, private val observer: Observer<T>)
    : Observer<T> {

    override fun onChanged(value: T) {
        observer.onChanged(value)
        output.transmit(value)
    }
}