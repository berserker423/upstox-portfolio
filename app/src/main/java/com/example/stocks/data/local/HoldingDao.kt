package com.example.stocks.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingDao {
    @Query("SELECT * FROM holding")
    fun observeAll(): Flow<List<LocalHolding>>

    @Query("SELECT * FROM holding")
    suspend fun getAll(): List<LocalHolding>

    @Upsert
    suspend fun upsertAll(tasks: List<LocalHolding>)

    @Query("DELETE FROM holding")
    suspend fun deleteAll()
}