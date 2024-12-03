package com.example.stocks.ui.holding_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stocks.data.modal.Holding
import com.example.stocks.databinding.ItemHoldingBinding

class HoldingListAdapter(
    private val data: List<Holding>,
) : RecyclerView.Adapter<HoldingListAdapter.HoldingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val itemBinding = ItemHoldingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HoldingViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        val content = data[position]
        holder.bind(content)
    }

    override fun getItemCount(): Int = data.size

    class HoldingViewHolder(private val binding: ItemHoldingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(holding: Holding) {
            binding.apply {
                qtValueTv.text = holding.quantity.toString()
                ltpValueTv.text = "₹ ${holding.ltp}"
                profitAndValueTv.text = "₹ ${holding.close}"
                stockNameTv.text = holding.symbol
            }
        }
    }
}