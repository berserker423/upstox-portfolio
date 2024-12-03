package com.example.stocks.data.network

import com.google.gson.annotations.SerializedName

data class NetworkHolding(
    @SerializedName("avgPrice")
    val avgPrice: Double,
    @SerializedName("close")
    val close: Double,
    @SerializedName("ltp")
    val ltp: Double,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("symbol")
    val symbol: String
)