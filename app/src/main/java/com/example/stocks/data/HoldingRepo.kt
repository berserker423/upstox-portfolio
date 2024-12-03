package com.example.stocks.data

import com.example.stocks.data.modal.Holding
import kotlinx.coroutines.flow.Flow

interface HoldingRepo {

   fun getHolding(): Flow<Result<List<Holding>>>

   suspend fun refresh(): Exception?
}