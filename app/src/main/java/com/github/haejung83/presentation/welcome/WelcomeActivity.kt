package com.github.haejung83.presentation.welcome

import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.haejung83.R
import com.github.haejung83.databinding.ActivityWelcomeBinding
import com.github.haejung83.extend.moveToActivity
import com.github.haejung83.extend.obtainViewModel
import com.github.haejung83.presentation.base.DataBindingAppCompatActivity
import com.github.haejung83.presentation.main.MainActivity

class WelcomeActivity : DataBindingAppCompatActivity<ActivityWelcomeBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_welcome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.viewmodel = obtainViewModel(WelcomeViewModel::class.java).apply {

            moveToMainEvent.observe(this@WelcomeActivity, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    moveToActivity(MainActivity::class.java)
                }
            })

        }
        viewDataBinding.lifecycleOwner = this
    }

}
