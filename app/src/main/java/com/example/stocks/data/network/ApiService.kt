package com.example.stocks.data.network

import retrofit2.http.GET

interface ApiService {
    @GET(".")
    suspend fun getHoldings(): NetworkResponse
}