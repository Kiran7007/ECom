package com.ecom.sample.utils

import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("android:bindText")
fun bindEditText(editText: EditText, value: CharSequence) {
    if (editText.text.toString() != value.toString()) {
        editText.setText(value)
        editText.setSelection(value.length)
    }
}