package com.example.stocks.data.modal

data class Holding(
    val id: Int?,
    val avgPrice: Double,
    val close: Double,
    val ltp: Double,
    val quantity: Int,
    val symbol: String
){
    val currentValue: Double
        get() = ltp * quantity

    val investmentValue: Double
        get() = avgPrice * quantity

    val profitAndLoss: Double
        get() = currentValue - investmentValue
}
