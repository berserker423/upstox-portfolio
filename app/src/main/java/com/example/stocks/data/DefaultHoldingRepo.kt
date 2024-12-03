package com.example.stocks.data


import com.example.stocks.data.local.StockDatabase
import com.example.stocks.data.network.ApiService
import com.example.stocks.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultHoldingRepo @Inject constructor(
    private val apiService: ApiService,
    private val stockDatabase: StockDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): HoldingRepo {
    override fun getHolding() = channelFlow {
        launch {
           refresh()?.let {
                send(Result.Error(it))
            }
        }
        stockDatabase.holdingDao().observeAll().map { holdings ->
            withContext(dispatcher) {
                holdings.toExternal()
            }
        }.collectLatest {
            send(Result.Success(it))
        }
    }.flowOn(dispatcher)
    
    override suspend fun refresh(): Exception? {
       val exception =  withContext(dispatcher) {
            try {
                val remoteTasks = apiService.getHoldings()
                stockDatabase.holdingDao().apply {
                    deleteAll()
                    upsertAll(remoteTasks.data.userHolding.toLocal())
                }
               null
            }catch (error : Exception){
                 error
            }
        }
        return exception
    }

}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}