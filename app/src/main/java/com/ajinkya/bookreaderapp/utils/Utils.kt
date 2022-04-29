package com.ajinkya.bookreaderapp.utils

import android.content.Context
import android.widget.Toast

fun toastMessage(message: String, context: Context, duration: ToastEnum) {
    Toast.makeText(
        context, message,
        when (duration) {
            ToastEnum.LongDuration -> Toast.LENGTH_LONG
            ToastEnum.ShortDuration -> Toast.LENGTH_SHORT
        }
    ).show()
}