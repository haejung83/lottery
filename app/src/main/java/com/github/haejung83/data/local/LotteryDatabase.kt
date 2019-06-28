package com.github.haejung83.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Lottery::class
    ],
    exportSchema = false,
    version = 1
)
abstract class LotteryDatabase : RoomDatabase() {

    abstract fun lotteryDao(): LotteryDao

    companion object {
        private var instance: LotteryDatabase? = null

        fun getInstance(context: Context): LotteryDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LotteryDatabase::class.java,
                    "lottery.db"
                ).build().apply {
                    instance = this
                }
            }
        }
    }

}