package com.github.haejung83.data.local

import androidx.paging.DataSource
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface LotteryDao {

    @Query("SELECT * FROM lottery")
    fun findAll(): Flowable<List<Lottery>>

    @Query("SELECT * FROM lottery ORDER BY drwNo ASC")
    fun findAllForPaging(): DataSource.Factory<Int, Lottery>

    @Query("SELECT * FROM lottery WHERE drwNo = :number")
    fun findByDrawNumber(number: Int): Flowable<Lottery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg lotteries: Lottery): Completable

    @Update
    fun update(lottery: Lottery): Completable

    @Query("DELETE FROM lottery WHERE drwNo = :number")
    fun deleteByDrawNumber(number: Int): Completable

    @Query("DELETE FROM lottery")
    fun deleteAll(): Completable

    @Query("SELECT count(*) FROM lottery")
    fun count(): Single<Int>

}