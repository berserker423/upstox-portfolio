package com.example.shared_test.data

import com.example.stocks.data.HoldingRepo
import com.example.stocks.data.Result
import com.example.stocks.data.modal.Holding
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class FakeRepo : HoldingRepo {

    var shouldThrowError = false
    var shouldReturnEmptyList = false


    private var savedHoldings : List<Holding> = emptyList()
    override fun getHolding() = channelFlow{
        launch {
            refresh()?.let {
                send(Result.Error(it))
            }
        }
        if(!shouldThrowError)
        send(Result.Success(savedHoldings))
    }


    override suspend fun refresh(): Exception? {
        if(!shouldThrowError) {
            if(shouldReturnEmptyList){
                savedHoldings =  emptyList()
            } else {
                savedHoldings = listOf(
                    Holding( 0, 2.0, 100.0, 120.0, 20, "Stock A",),
                    Holding(1, 3.0, 300.0, 350.0, 50, "Stock B",)
                )
            }
            return null
        } else {
           return Exception("testing")
        }
    }
}