package com.ecom.sample.utils

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.ecom.sample.R

@BindingAdapter("android:bindText")
fun bindEditText(editText: EditText, value: CharSequence) {
    if (editText.text.toString() != value.toString()) {
        if (value.isNotEmpty()) {
            editText.setBackgroundResource(R.drawable.entered_quantity_bg)
        } else {
            editText.setBackgroundResource(R.drawable.quantity_bg)
        }

        editText.setText(value)
        editText.setSelection(value.length)
    }
}