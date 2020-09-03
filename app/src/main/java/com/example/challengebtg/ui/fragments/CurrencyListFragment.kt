package com.example.challengebtg.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.challengebtg.MyApplication
import com.example.challengebtg.R
import com.example.challengebtg.dao.CurrenciesDao
import com.example.challengebtg.databinding.FragmentCurrencyListBinding
import com.example.challengebtg.model.Currency
import com.example.challengebtg.model.Filter
import com.example.challengebtg.ui.MainActivity
import com.example.challengebtg.ui.adapter.AdapterCurrencyList
import com.example.challengebtg.utils.ViewAnimation
import com.example.challengebtg.utils.extension.initGridLayout
import com.example.challengebtg.utils.extension.onTextChanged
import com.example.challengebtg.utils.extension.setSearchToolbar
import com.example.challengebtg.viewModel.CurrencyViewlModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_currency_list.*

class CurrencyListFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private lateinit var currencyListBinding: FragmentCurrencyListBinding
    private val viewModel: CurrencyViewlModel by activityViewModels()
    private val animation = ViewAnimation()
    private var viewFocus: View? = null
    private var needFocus = false
    private var isCollapse = true

    private val adapter by lazy {
        AdapterCurrencyList(viewModel)
    }

    private val currenciesObserver = Observer<ArrayList<Currency>> { list ->
        recyclerView.isGone = list.isEmpty()
        textViewEmpty.isGone = list.isNotEmpty()
        adapter.replace(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currencyListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_currency_list, container, false)
        return currencyListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilter()
        initLiveData()
        initToolbar()
        initInputText()
        initAdapter()
    }

    private fun initFilter() {
        viewModel.filter = Filter()
    }

    private fun initAdapter() {
        recyclerView.initGridLayout(adapter)
        val currencies = CurrenciesDao().load()?.results ?: arrayListOf()
        adapter.replace(currencies)
    }

    private fun initInputText() = editTextSearch.onTextChanged(viewModel.textSearchChange)

    override fun onResume() {
        super.onResume()
        if (isCollapse) (activity as MainActivity).toolbar.menu.performIdentifierAction(
            R.id.action_search,
            0
        )
    }

    private fun initLiveData() =
        viewModel.currencyLiveData.observe(viewLifecycleOwner, currenciesObserver)

    private fun initToolbar() = setSearchToolbar("Currencies", this)

    private fun animateSearch() = if (isCollapse) closeSearch() else openSearch()

    private fun closeSearch() = animation.decreaseViewSize(constraintSearch, 70, duration = 450) {
        needFocus = false
        if (editTextSearch != null) viewFocus = editTextSearch
        viewFocus?.clearFocus()
    }

    private fun openSearch() = animation.increaseViewSize(constraintSearch, 70, duration = 450) {
        needFocus = true
        if (editTextSearch != null) viewFocus = editTextSearch
        viewFocus?.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        clearFocusKeyBoard()
        clearToolbar()
        removeViewModel()
    }

    private fun clearFocusKeyBoard() {
        if (!isCollapse) (activity as MainActivity).toolbar.menu.performIdentifierAction(
            R.id.action_search,
            0
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetCurrencies()
    }

    private fun clearToolbar() = (activity as MainActivity).toolbar.menu.clear()

    private fun removeViewModel() = viewModel.currencyLiveData.removeObserver(currenciesObserver)

    override fun onMenuItemClick(item: MenuItem): Boolean {
        val id: Int = item.itemId
        when (id) {
            R.id.action_search -> {
                if (animation.inAnimation) return false
                isCollapse = !isCollapse
                item.icon = getSearchIcon(isCollapse)
                animateSearch()
                return true
            }
            R.id.codeFilter -> {
                viewModel.setOrderFilter("code")
                return true
            }
            R.id.nameFilter -> {
                viewModel.setOrderFilter("name")
                return true
            }
            else -> return false
        }
    }

    fun getSearchIcon(isCollapse: Boolean): Drawable? =
        if (isCollapse) MyApplication.context.getDrawable(R.drawable.ic_search)
        else MyApplication.context.getDrawable(R.drawable.ic_close)
}