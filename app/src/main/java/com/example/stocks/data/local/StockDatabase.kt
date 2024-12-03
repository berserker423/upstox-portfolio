package com.example.stocks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalHolding::class], version = 1, exportSchema = false)
abstract class StockDatabase : RoomDatabase() {

    abstract fun holdingDao(): HoldingDao
}