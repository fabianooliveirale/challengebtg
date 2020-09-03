package com.example.challengerbtg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerbtg.R
import com.example.challengerbtg.databinding.AdapterHistoryBinding
import com.example.challengerbtg.model.QuoteResult

class AdapterHistory() : RecyclerView.Adapter<AdapterHistory.HistoryViewHolder>() {
    private var list = ArrayList<QuoteResult>()

    inner class HistoryViewHolder(
        val adapter: AdapterHistoryBinding
    ) : RecyclerView.ViewHolder(adapter.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterHistory.HistoryViewHolder {
        val binding = DataBindingUtil.inflate<AdapterHistoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.adapter_history,
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterHistory.HistoryViewHolder, position: Int) {
        holder.adapter.history = list[position]
    }

    override fun getItemCount(): Int = list.count()

    fun replace(list: ArrayList<QuoteResult>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }
}