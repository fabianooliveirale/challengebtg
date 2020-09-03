package com.example.challengerbtg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerbtg.R
import com.example.challengerbtg.databinding.AdapterCurrencyBinding
import com.example.challengerbtg.model.Currency
import com.example.challengerbtg.viewModel.CurrencyViewlModel

class AdapterCurrencyList(val viewModel: CurrencyViewlModel) :
    RecyclerView.Adapter<AdapterCurrencyList.CurrencyViewHolder>() {
    private var list = ArrayList<Currency>()

    inner class CurrencyViewHolder(
        val adapter: AdapterCurrencyBinding
    ) : RecyclerView.ViewHolder(adapter.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterCurrencyList.CurrencyViewHolder {
        val binding = DataBindingUtil.inflate<AdapterCurrencyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.adapter_currency,
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterCurrencyList.CurrencyViewHolder, position: Int) {
        holder.adapter.currency = list[position]
        holder.adapter.viewModel = viewModel
    }

    override fun getItemCount(): Int = list.count()

    fun replace(list: ArrayList<Currency>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }
}