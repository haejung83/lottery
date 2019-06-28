package com.github.haejung83.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LotteryAPI {

    @GET("/common.do")
    fun getLottery(
        @Query("method") method: String = "getLottoNumber",
        @Query("drwNo") drawNumber: Int
    ): Call<LotteryDTO>

    companion object {
        const val BASE_URL = "https://www.dhlottery.co.kr"
    }

}