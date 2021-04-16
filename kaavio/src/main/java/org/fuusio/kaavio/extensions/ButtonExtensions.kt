package org.fuusio.kaavio.extensions

import android.widget.Button
import org.fuusio.kaavio.Rx

infix fun Button.connect(receiver: Rx<Unit>) {
    receiver.onReceive(Unit)
}