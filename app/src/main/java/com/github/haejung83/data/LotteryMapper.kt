package com.github.haejung83.data

import com.github.haejung83.data.local.Lottery
import com.github.haejung83.data.remote.LotteryDTO


fun getLotteryFromDto(lotteryDTO: LotteryDTO): Lottery =
    with(lotteryDTO) {
        Lottery(
            drwNo,
            drwtNo1,
            drwtNo2,
            drwtNo3,
            drwtNo4,
            drwtNo5,
            drwtNo6,
            bnusNo
        )
    }
