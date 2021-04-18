package org.fuusio.kaavio.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import org.fuusio.kaavio.Rx

infix fun EditText.connect(receiver: Rx<String>) {
    addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null) {
                receiver.onReceive(editable.toString())
            }
        }
    })
}

fun EditText.onTextChanged(action: (CharSequence) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(string: Editable?) = Unit
        override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
            action(string ?: "")
        }
    })
}

fun EditText.clearOnTextChangedListener() {
    onTextChanged {}
}