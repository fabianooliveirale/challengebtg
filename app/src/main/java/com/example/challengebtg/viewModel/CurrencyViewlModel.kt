package com.example.challengebtg.viewModel

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.challengebtg.MyApplication
import com.example.challengebtg.R
import com.example.challengebtg.api.requests.RequestGetQuotes
import com.example.challengebtg.api.requests.RequestToListCurrencies
import com.example.challengebtg.dao.CurrenciesDao
import com.example.challengebtg.dao.HistoryDao
import com.example.challengebtg.model.*
import com.example.challengebtg.utils.Convert
import com.example.challengebtg.utils.CurrenciesFilter
import com.example.challengebtg.utils.extension.debounce
import com.example.challengebtg.utils.extension.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CurrencyViewlModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()

    var quotes = ArrayList<Quotation>()
    var filter = Filter()

    var isCurrencyFrom = true

    val resultLiveData = MutableLiveData<String>()
    val currencyLiveData = MutableLiveData<ArrayList<Currency>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    var selectedCurrencyFrom: Currency? = null
    var selectedCurrencyTo: Currency? = null

    /**
     * Filter
     **/

    init {
        requestToListCurrencies()
        requestGetQuotes()
    }

    val textSearchChange: (String) -> Unit = debounce(
        500L,
        this
    ) { searchString ->
        setTextFilter(searchString)
    }

    fun setOrderFilter(order: String) {
        filter.order = order
        applyFilter()
    }

    fun setTextFilter(text: String) {
        filter.text = text
        applyFilter()
    }

    private fun applyFilter() {
        val currencies = CurrenciesDao().load()?.results ?: arrayListOf()
        val newCurrencies = CurrenciesFilter().apply(currencies, filter)
        currencyLiveData.postValue(newCurrencies)
    }

    /**
     *  Convert
     **/

    fun convert(amount: String) {
        if (selectedCurrencyFrom == null || selectedCurrencyTo == null || amount == "") {
            resultLiveData.postValue(MyApplication.context.getString(R.string.no_results_found))
            return
        }

        val amountResult = Convert().currency(
            selectedCurrencyFrom ?: Currency(),
            selectedCurrencyTo ?: Currency(),
            quotes,
            amount.toDouble()
        )

        val resultString =
            "$amount ${selectedCurrencyFrom?.code} = $amountResult ${selectedCurrencyTo?.code}"

        saveNewQuoteResult(resultString)
        resultLiveData.postValue(resultString)
    }

    private fun saveNewQuoteResult(result: String) {
        val dao = HistoryDao()
        val history = dao.load() ?: History()
        history.results.add(QuoteResult(result = result))
        dao.save(history)
    }

    /**
     *  Requests
     **/

    fun requestGetQuotes() {
        loadingLiveData.postValue(true)
        RequestGetQuotes { quotes, error ->
            loadingLiveData.postValue(false)
            if (error != null) errorLiveData.postValue(error)
            if (quotes != null) this.quotes = quotes
        }
    }

    fun requestToListCurrencies() {
        loadingLiveData.postValue(true)
        RequestToListCurrencies { apiCurrencies, error ->
            loadingLiveData.postValue(false)
            if (error != null) errorLiveData.postValue(error)
            if (apiCurrencies != null) saveCurrencies(apiCurrencies)
        }
    }

    private fun saveCurrencies(result: ArrayList<Currency>) {
        val dao = CurrenciesDao()
        val currencies = dao.load() ?: Currencies()
        currencies.results = result
        dao.save(currencies)
    }

    /**
     *  Navigation
     **/

    fun gotoCurrencyList(view: View, isCurrencyFrom: Boolean = true) {
        val dao = CurrenciesDao()
        val currencies = dao.load() ?: Currencies()
        if (currencies.results.isNotEmpty()) {
            this.isCurrencyFrom = isCurrencyFrom
            view.findNavController().navigate(R.id.to_currencyListFragment)
        }else{
            (view.context as Activity).toast(view.context.getString(R.string.no_results_found))
        }
    }

    /**
     *  Currency
     **/

    fun selectCurrency(view: View, currency: Currency) {
        if (isCurrencyFrom) setCurrencyFrom(currency) else setCurrencyTo(currency)
        val activity = view.context as Activity
        activity.onBackPressed()
    }

    fun resetCurrencies() {
        val currencies = CurrenciesDao().load()?.results ?: arrayListOf()
        currencyLiveData.postValue(currencies)
    }

    private fun setCurrencyFrom(currency: Currency) {
        selectedCurrencyFrom = currency
    }

    private fun setCurrencyTo(currency: Currency) {
        selectedCurrencyTo = currency
    }
}