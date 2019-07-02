package com.github.haejung83.presentation.retrieve

import android.os.Bundle
import com.github.haejung83.R
import com.github.haejung83.databinding.ActivityRetrieveBinding
import com.github.haejung83.extend.obtainViewModel
import com.github.haejung83.presentation.base.DataBindingAppCompatActivity
import timber.log.Timber

class RetrieveActivity : DataBindingAppCompatActivity<ActivityRetrieveBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_retrieve

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.viewmodel = obtainViewModel(RetrieveViewModel::class.java)
        viewDataBinding.lifecycleOwner = this

        val drawNumber = getDrawNumberFromIntent()
        viewDataBinding.viewmodel?.showLotteryByDrawNumber(drawNumber)
    }

    private fun getDrawNumberFromIntent() =
        intent?.data?.let {
            Timber.i("scheme ${it.scheme}, host: ${it.host}")
            it.host?.toInt()
        } ?: 0

}
