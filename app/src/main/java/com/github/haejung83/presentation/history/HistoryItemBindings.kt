package com.github.haejung83.presentation.history

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.haejung83.data.local.Lottery

object HistoryItemBindings {

    @BindingAdapter("text_draw_number")
    @JvmStatic
    fun setTextDrawNumber(textView: TextView, lottery: Lottery?) {
        lottery?.let {
            textView.text = it.drawNo.toString()
        }
    }

    @BindingAdapter("text_six_numbers")
    @JvmStatic
    fun setTextSixNumbers(textView: TextView, lottery: Lottery?) {
        lottery?.let {
            textView.text =
                listOf(it.drawNo1, it.drawNo2, it.drawNo3, it.drawNo4, it.drawNo5, it.drawNo6).joinToString()
        }
    }

    @BindingAdapter("text_bonus_number")
    @JvmStatic
    fun setTextBonusNumber(textView: TextView, lottery: Lottery?) {
        lottery?.let {
            textView.text = it.bonusNo.toString()
        }
    }

}