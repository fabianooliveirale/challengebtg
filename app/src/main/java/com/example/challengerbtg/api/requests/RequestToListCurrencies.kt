package com.example.challengerbtg.api.requests

import android.util.Log
import com.example.challengerbtg.api.NetworkApi
import com.example.challengerbtg.model.Currency
import com.example.challengerbtg.model.CurrencyListResponse
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RequestToListCurrencies(var closure: (response: ArrayList<Currency>?, error: String?) -> (Unit)) {
    init {
        try {
            init()
        } catch (e: Exception) {
            val error = "CurrencyListError_3: ${e.message}"
            Log.d("Error_Log_D", error)
            closure(null, error)
        }
    }


    private fun init() {
        val observable =
            NetworkApi.public().currencyList()
        observable.subscribeOn(Schedulers.newThread())
            .subscribe(object : Observer<CurrencyListResponse> {
                override fun onComplete() {}

                override fun onNext(value: CurrencyListResponse?) {
                    if (value?.success != true) {
                        val error = "CurrencyListError_1"
                        Log.d("Error_Log_D", error)
                        closure(null, error)
                        return
                    }

                    val currencies = value.currencies.map {
                        Currency(code = it.key, name = it.value)
                    } as ArrayList

                    closure(currencies, null)
                }

                override fun onError(e: Throwable?) {
                    val error = "CurrencyListError_2: ${e?.message}"
                    Log.d("Error_Log_D", error)
                    closure(null, error)
                }

                override fun onSubscribe(d: Disposable?) {}
            })
    }

}
