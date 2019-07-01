package com.github.haejung83.presentation.history

import android.os.Bundle
import com.github.haejung83.R
import com.github.haejung83.databinding.ActivityHistoryBinding
import com.github.haejung83.extend.obtainViewModel
import com.github.haejung83.presentation.base.DataBindingAppCompatActivity

class HistoryActivity : DataBindingAppCompatActivity<ActivityHistoryBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.viewmodel = obtainViewModel(HistoryViewModel::class.java)
        viewDataBinding.lifecycleOwner = this
    }
}
