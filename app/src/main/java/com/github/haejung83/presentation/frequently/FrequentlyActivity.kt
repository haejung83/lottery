package com.github.haejung83.presentation.frequently

import android.os.Bundle
import com.github.haejung83.R
import com.github.haejung83.databinding.ActivityFrequentlyBinding
import com.github.haejung83.extend.obtainViewModel
import com.github.haejung83.presentation.base.DataBindingAppCompatActivity

class FrequentlyActivity : DataBindingAppCompatActivity<ActivityFrequentlyBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_frequently

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.viewmodel = obtainViewModel(FrequentlyViewModel::class.java)
        viewDataBinding.lifecycleOwner = this

        viewDataBinding.viewmodel?.showSortedLotteryNumberRank()
    }

}
