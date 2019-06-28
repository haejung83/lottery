package com.github.haejung83.data

import android.content.Context
import com.github.haejung83.data.local.LotteryDatabase

fun provideLotteryRepository(context: Context) =
    LotteryRepository(LotteryDatabase.getInstance(context).lotteryDao())
