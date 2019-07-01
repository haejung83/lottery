package com.github.haejung83.extend

import android.content.Intent
import androidx.fragment.app.Fragment

fun <T> Fragment.startActivity(clazz: Class<T>) {
    val intent = Intent(context, clazz)
    startActivity(intent)
}