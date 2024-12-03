package com.example.stocks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "holding"
)
data class LocalHolding(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val avgPrice: Double,
    val close: Double,
    val ltp: Double,
    val quantity: Int,
    val symbol: String
)