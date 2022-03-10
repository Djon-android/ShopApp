package com.example.shopapp.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.shopapp.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorName")
fun bindNameError(tilName: TextInputLayout, isError: Boolean) {
        if (isError) {
            tilName.error = tilName.context.getString(R.string.enter_name_value)
        } else {
            tilName.error = null
        }
}

@BindingAdapter("errorCount")
fun bindCountError(tilCount: TextInputLayout, isError: Boolean) {
        if (isError) {
            tilCount.error = tilCount.context.getString(R.string.enter_name_value)
        } else {
            tilCount.error = null
        }
}