package com.example.stocks

import com.example.shared_test.MainCoroutineRule
import com.example.shared_test.data.FakeRepo
import com.example.stocks.data.modal.Holding
import com.example.stocks.data.modal.Stats
import com.example.stocks.ui.holding_view.HoldingViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HoldingViewModelTest {

    private lateinit var holdingViewModel: HoldingViewModel

    private lateinit var holdingRepo: FakeRepo

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(testDispatcher)
        holdingRepo = FakeRepo()

        holdingViewModel = HoldingViewModel(holdingRepo)
    }

    @Test
    fun `fetchHoldings updates UIState with correct holdings and stats`() = runTest {
        val expectedHoldings = listOf(
            Holding( 0, 2.0, 100.0, 120.0, 20, "Stock A",),
            Holding(1, 3.0, 300.0, 350.0, 50, "Stock B",)
        )
        val expectedStats = Stats(
            totalCurrentValue=19898.0,
            totalInvestment=190.0,
            todaysProfitAndLoss=-2900.0,
            totalProfitAndLoss=19708.0
        )

        holdingViewModel.fetchHoldings()
        advanceUntilIdle()
        val uiState = holdingViewModel.uiState.first()

        assertEquals(expectedHoldings, uiState.holdings)
        assertEquals(expectedStats, uiState.stats)
    }


    @Test
    fun `fetchHoldings handles empty holdings list`() = runTest {
        holdingRepo.shouldReturnEmptyList = true
        holdingViewModel.fetchHoldings()
        advanceUntilIdle()
        val uiState = holdingViewModel.uiState.first()
        assertEquals(emptyList<Holding>(), uiState.holdings)
        assertEquals(Stats(), uiState.stats)
    }

    @Test
    fun `fetchHoldings handles error state`() = runTest {
        holdingRepo.shouldThrowError= true
        holdingViewModel.fetchHoldings()
        advanceUntilIdle()
        val uiState = holdingViewModel.uiState.drop(0).first()
        assertEquals(true,uiState.showToast)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}