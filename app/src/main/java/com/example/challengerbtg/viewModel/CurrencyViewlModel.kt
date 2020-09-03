package com.example.challengerbtg.viewModel

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.challengerbtg.MyApplication
import com.example.challengerbtg.R
import com.example.challengerbtg.api.requests.RequestGetQuotes
import com.example.challengerbtg.api.requests.RequestToListCurrencies
import com.example.challengerbtg.dao.CurrenciesDao
import com.example.challengerbtg.dao.HistoryDao
import com.example.challengerbtg.model.*
import com.example.challengerbtg.utils.Convert
import com.example.challengerbtg.utils.Filter
import com.example.challengerbtg.utils.extension.debounce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CurrencyViewlModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()

    var quotes = ArrayList<Quotation>()
    var result = ""

    var isCurrencyFrom = true

    val resultLiveData = MutableLiveData<String>()
    val currencyLiveData = MutableLiveData<ArrayList<Currency>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    var selectedCurrencyFrom: Currency? = null
    var selectedCurrencyTo: Currency? = null

    val textSearchChange: (String) -> Unit = debounce(
        500L,
        this
    ) { searchString ->
        val currencies = CurrenciesDao().load()?.results ?: arrayListOf()
        val newCurrencies = Filter().currencies(currencies, searchString)
        currencyLiveData.postValue(newCurrencies)
    }

    fun convert(amount: String) {
        if (selectedCurrencyFrom == null || selectedCurrencyTo == null || amount == "") {
            resultLiveData.postValue("")
            return
        }

        val amountResult = Convert().currency(
            selectedCurrencyFrom ?: Currency(),
            selectedCurrencyTo ?: Currency(),
            quotes,
            amount
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

    fun gotoCurrencyList(view: View, isCurrencyFrom: Boolean = true) {
        val dao = CurrenciesDao()
        val currencies = dao.load() ?: Currencies()
        if (currencies.results.isNotEmpty()) {
            this.isCurrencyFrom = isCurrencyFrom
            view.findNavController().navigate(R.id.to_currencyListFragment)
        }
    }

    fun selectCurrency(view: View, currency: Currency) {
        if (isCurrencyFrom) setCurrencyFrom(currency) else setCurrencyTo(currency)
        val activity = view.context as Activity
        activity.onBackPressed()
    }

    fun resetCurrencies() {
        val currencies = CurrenciesDao().load()?.results ?: arrayListOf()
        currencyLiveData.postValue(currencies)
    }

    fun getSearchIcon(isCollapse: Boolean): Drawable? =
        if (isCollapse) MyApplication.context.getDrawable(R.drawable.ic_search)
        else MyApplication.context.getDrawable(R.drawable.ic_close)

    private fun setCurrencyFrom(currency: Currency) {
        selectedCurrencyFrom = currency
    }

    private fun setCurrencyTo(currency: Currency) {
        selectedCurrencyTo = currency
    }
}