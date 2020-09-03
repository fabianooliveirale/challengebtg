package com.example.challengebtg.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.challengebtg.R
import com.example.challengebtg.dao.HistoryDao
import com.example.challengebtg.databinding.FragmentHistoryBinding
import com.example.challengebtg.ui.adapter.AdapterHistory
import com.example.challengebtg.utils.extension.initGridLayout
import com.example.challengebtg.utils.extension.setToolbar
import kotlinx.android.synthetic.main.fragment_currency_list.*


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private val adapter by lazy {
        AdapterHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initToolbar()
    }

    private fun initAdapter() {
        recyclerView.initGridLayout(adapter)
        adapter.replace(HistoryDao().load()?.results ?: arrayListOf())
    }

    private fun initToolbar() = setToolbar("History")
}