package com.example.challengerbtg.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.challengerbtg.R
import com.example.challengerbtg.databinding.HomeFragmentBinding
import com.example.challengerbtg.utils.extension.hideToolbar
import com.example.challengerbtg.utils.extension.showToolbar
import com.example.challengerbtg.utils.extension.toast
import com.example.challengerbtg.viewModel.CurrencyViewlModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private val viewModel: CurrencyViewlModel by activityViewModels()
    lateinit var fragmentHomeBinding: HomeFragmentBinding

    private val loadingObserver = Observer<Boolean> { isLoading ->
        progressBarFrom.isGone = !isLoading
        progressBarTo.isGone = !isLoading
    }

    private val resultObserver = Observer<String> { result ->
        textViewResult.text = result
    }

    private val errorObserver = Observer<String> { error ->
        activity?.toast(error)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.requestToListCurrencies()
        viewModel.requestGetQuotes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        fragmentHomeBinding.viewModel = viewModel

        return fragmentHomeBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initButton()
        initLiveData()
    }

    override fun onResume() {
        super.onResume()
        hideToolbar()
    }

    private fun initButton() {
        buttonHistory.setOnClickListener {
            view?.findNavController()?.navigate(R.id.to_historyFragment)
        }
    }

    private fun initLiveData() {
        viewModel.resultLiveData.observe(viewLifecycleOwner, resultObserver)
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
    }

    private fun clearLiveData() {
        viewModel.resultLiveData.removeObserver(resultObserver)
        viewModel.loadingLiveData.removeObserver(loadingObserver)
        viewModel.errorLiveData.removeObserver(errorObserver)
    }

    override fun onStop() {
        super.onStop()
        showToolbar()
        clearLiveData()
    }
}