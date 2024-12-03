package com.example.stocks.data.network


import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    @SerializedName("data")
    val data: HoldingResponse
)

data class HoldingResponse(
    @SerializedName("userHolding")
    val userHolding: List<NetworkHolding>
)