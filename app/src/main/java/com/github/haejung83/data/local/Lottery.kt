package com.github.haejung83.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lottery")
data class Lottery(
    @ColumnInfo(name = "drwNo") val drawNo: Int,
    @ColumnInfo(name = "drwtNo1") val drawNo1: Int,
    @ColumnInfo(name = "drwtNo2") val drawNo2: Int,
    @ColumnInfo(name = "drwtNo3") val drawNo3: Int,
    @ColumnInfo(name = "drwtNo4") val drawNo4: Int,
    @ColumnInfo(name = "drwtNo5") val drawNo5: Int,
    @ColumnInfo(name = "drwtNo6") val drawNo6: Int,
    @ColumnInfo(name = "bnusNo") val bonusNo: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    fun toSixNumberList() =
        listOf(drawNo1, drawNo2, drawNo3, drawNo4, drawNo5, drawNo6)
}