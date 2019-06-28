package com.github.haejung83.presentation.splash

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.github.haejung83.R
import com.github.haejung83.databinding.ActivitySplashBinding
import com.github.haejung83.extend.moveToActivity
import com.github.haejung83.extend.obtainViewModel
import com.github.haejung83.extend.showToast
import com.github.haejung83.presentation.base.DataBindingAppCompatActivity
import com.github.haejung83.presentation.main.MainActivity
import com.github.haejung83.presentation.splash.SplashViewModel.MoveToClass.MoveToMain
import com.github.haejung83.presentation.splash.SplashViewModel.MoveToClass.MoveToWelcome
import com.github.haejung83.presentation.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : DataBindingAppCompatActivity<ActivitySplashBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.viewmodel = obtainViewModel(SplashViewModel::class.java).apply {
            moveToEvent.observe(this@SplashActivity, Observer { event ->
                event.getContentIfNotHandled()?.let { moveToClass ->
                    when (moveToClass) {
                        is MoveToMain -> moveToActivity(MainActivity::class.java)
                        is MoveToWelcome -> moveToActivity(WelcomeActivity::class.java)
                    }
                }
            })
            refreshErrorEvent.observe(this@SplashActivity, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    container_layout.showToast(it, Toast.LENGTH_SHORT)
                    finish()
                }
            })
        }
        viewDataBinding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.viewmodel?.checkLotteryAndRefreshIfNeeds()
    }

}
