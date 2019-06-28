package com.github.haejung83.presentation.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.haejung83.presentation.Event

class WelcomeViewModel : ViewModel() {

    private val _moveToMainEvent = MutableLiveData<Event<Unit>>()
    val moveToMainEvent: LiveData<Event<Unit>>
        get() = _moveToMainEvent

    fun moveToMain() {
        _moveToMainEvent.value = Event(Unit)
    }

}