package com.github.haejung83.presentation.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.haejung83.R
import com.github.haejung83.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        bindings()
    }

    private fun bindings() {
        button_next.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}
