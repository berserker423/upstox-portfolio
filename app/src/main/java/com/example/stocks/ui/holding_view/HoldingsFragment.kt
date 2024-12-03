package com.example.stocks.ui.holding_view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocks.R
import com.example.stocks.databinding.FragmentFirstBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoldingsFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel : HoldingViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var expansion: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                binding.progressView.isVisible = it.showLoader
                if(it.showToast){
                    Snackbar.make(binding.clTotalProfitView, "Something went wrong", Snackbar.LENGTH_LONG)
                        .show()
                }
                if(it.holdings.isNotEmpty()){
                    binding.holdingsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                        adapter = HoldingListAdapter(it.holdings)
                    }

                    binding.profitValueTv.text = "₹ ${it.stats.totalProfitAndLoss.toLong()}"
                    binding.todayPnlProfitValueTv.text = "₹ ${it.stats.todaysProfitAndLoss.toLong()}"
                    binding.currentValueTv.text = "₹ ${it.stats.totalCurrentValue.toLong()}"
                    binding.investmentValueTv.text = "₹ ${it.stats.totalInvestment.toLong()}"
                    binding.clTotalProfitView.isVisible = true
                }
            }
        }
        binding.ivExpand.setOnClickListener {
            if (!expansion) {
                binding.clPnlInfo.isVisible = true
                binding.ivExpand.setImageResource(R.drawable.ic_up_arrow)
                expansion = true
            }
            else{
                binding.clPnlInfo.isVisible = false
                binding.ivExpand.setImageResource(R.drawable.ic_down_arrow)
                expansion = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}