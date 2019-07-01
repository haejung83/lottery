package com.github.haejung83.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.haejung83.R
import com.github.haejung83.databinding.MainFragmentBinding
import com.github.haejung83.extend.obtainViewModel
import com.github.haejung83.presentation.base.DataBindingFragment

class MainFragment : DataBindingFragment<MainFragmentBinding>() {

    override val layoutResId: Int
        get() = R.layout.main_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewmodel = (activity as AppCompatActivity).obtainViewModel(MainViewModel::class.java).apply {

        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
