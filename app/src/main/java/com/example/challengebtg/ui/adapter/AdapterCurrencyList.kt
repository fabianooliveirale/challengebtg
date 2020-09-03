package com.example.challengebtg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.challengebtg.R
import com.example.challengebtg.databinding.AdapterCurrencyBinding
import com.example.challengebtg.model.Currency
import com.example.challengebtg.viewModel.CurrencyViewlModel

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
        holder.adapter.position = position
    }

    override fun getItemCount(): Int = list.count()

    fun replace(list: ArrayList<Currency>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }
}