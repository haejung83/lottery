package com.github.haejung83.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.data.provideLotteryRepository

class ViewModelFactory private constructor(
    private val lotteryRepository: LotteryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
//                isAssignableFrom(MarkViewModel::class.java) -> MarkViewModel(markRepository)
//                isAssignableFrom(PresetViewModel::class.java) -> PresetViewModel(markPresetRepository)
//                isAssignableFrom(SnsViewModel::class.java) -> SnsViewModel()
//                isAssignableFrom(AddMarkViewModel::class.java) -> AddMarkViewModel(markRepository)
//                isAssignableFrom(SnapViewModel::class.java) -> SnapViewModel(markRepository, markPresetRepository)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    provideLotteryRepository(context)
                ).apply {
                    instance = this
                }
            }
    }

}