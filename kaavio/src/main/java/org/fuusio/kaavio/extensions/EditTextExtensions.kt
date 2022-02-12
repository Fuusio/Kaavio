package org.fuusio.kaavio.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.Rx

/**
 * Connects this [EditText] to the given receiver [Rx] in a way that whenever the text is changed,
 * the text is send to the attached receiver as a [String].
 */
infix fun EditText.connect(receiver: Rx<String>) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null) receiver.onReceive(Ctx(), editable.toString())
        }
    })
}

/**
 * Attaches the given [action] to this [EditText] by using a [TextWatcher] in a way that when ever
 * the edited text changes the [action] get executed.
 */
fun EditText.onTextChanged(action: (CharSequence) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
            action(string ?: "")
        }
        override fun afterTextChanged(string: Editable?) = Unit
    })
}

fun EditText.clearOnTextChangedListener() {
    onTextChanged {}
}