package com.github.haejung83.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.haejung83.R
import com.github.haejung83.databinding.MainFragmentBinding
import com.github.haejung83.extend.obtainViewModel
import com.github.haejung83.extend.startActivity
import com.github.haejung83.presentation.base.DataBindingFragment
import com.github.haejung83.presentation.history.HistoryActivity
import com.github.haejung83.presentation.main.MainViewModel.OpenScreen.OpenFrequentlyScreen
import com.github.haejung83.presentation.main.MainViewModel.OpenScreen.OpenHistoryScreen

class MainFragment : DataBindingFragment<MainFragmentBinding>() {

    override val layoutResId: Int
        get() = R.layout.main_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewmodel = (activity as AppCompatActivity).obtainViewModel(MainViewModel::class.java).apply {
            openScreenEvent.observe(this@MainFragment, Observer { event ->
                event.getContentIfNotHandled()?.let { openScreen ->
                    when (openScreen) {
                        is OpenHistoryScreen -> startActivity(HistoryActivity::class.java)
                        is OpenFrequentlyScreen -> TODO("not implmented yet")
                    }
                }
            })
        }
        viewDataBinding.lifecycleOwner = this
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
