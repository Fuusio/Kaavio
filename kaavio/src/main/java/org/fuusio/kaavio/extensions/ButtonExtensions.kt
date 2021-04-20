package org.fuusio.kaavio.extensions

import android.widget.Button
import org.fuusio.kaavio.Rx

/**
 * Connects this [Button] to the given receiver [Rx] in a way that whenever the button is clicked,
 * a [Unit] is send to attached receiver.
 */
infix fun Button.connect(receiver: Rx<Unit>) {
    setOnClickListener { receiver.onReceive(Unit) }
}