package com.github.haejung83.extend

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.haejung83.presentation.Event

fun View.showToast(message: String, duration: Int) =
    Toast.makeText(this.context, message, duration).show()

fun View.setupToast(lifecycleOwner: LifecycleOwner, toastEvent: LiveData<Event<Int>>, timeLength: Int) =
    toastEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { showToast(context.getString(it), timeLength) }
    })

