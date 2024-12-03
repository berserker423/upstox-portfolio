package com.example.stocks.ui.holding_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocks.data.HoldingRepo
import com.example.stocks.data.Result
import com.example.stocks.data.modal.Holding
import com.example.stocks.data.modal.Stats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingViewModel @Inject constructor(
   private val holdingRepo: HoldingRepo
): ViewModel() {

    init {
        fetchHoldings()
    }
    private val _uiState  = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()




    fun fetchHoldings(){
        viewModelScope.launch {
            holdingRepo.getHolding().collectLatest {
                when(it){
                    is Result.Success -> {
                        if(it.data.isNotEmpty()){
                            var totalCurrentValue = 0.0
                            var totalInvestment = 0.0
                            var todaysProfitAndLoss =  0.0
                            it.data.forEach{ userHolding->
                                totalCurrentValue += userHolding.currentValue.dec()
                                totalInvestment += userHolding.investmentValue
                                todaysProfitAndLoss += (userHolding.close - userHolding.ltp) * userHolding.quantity
                            }
                            _uiState.value = _uiState.value.copy(
                                showLoader = false,
                                holdings = it.data,
                                stats = Stats(
                                    totalCurrentValue = totalCurrentValue,
                                    totalInvestment = totalInvestment,
                                    todaysProfitAndLoss = todaysProfitAndLoss,
                                    totalProfitAndLoss = totalCurrentValue - totalInvestment
                                )
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            showToast = true,
                            showLoader = false
                        )
                    }
                }

            }
        }
    }
}

data class UIState(
    val holdings: List<Holding> = emptyList(),
    val stats: Stats = Stats(),
    val showToast : Boolean = false,
    val showLoader : Boolean = true
)
