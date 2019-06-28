package com.github.haejung83.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.haejung83.R
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

}
