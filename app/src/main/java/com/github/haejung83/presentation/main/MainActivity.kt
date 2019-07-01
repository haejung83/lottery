package com.github.haejung83.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.haejung83.R
import com.github.haejung83.extend.replaceFragmentInActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragmentInActivity(MainFragment.newInstance(), R.id.container_fragment)
    }

}
