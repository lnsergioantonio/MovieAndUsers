package com.example.movieandusers.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged.invoke(charSequence.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
}

fun TextInputEditText.isEmpty():Boolean{
    return text.isNullOrEmpty()
}