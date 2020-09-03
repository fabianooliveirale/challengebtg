package com.example.challengebtg.api.requests

import android.util.Log
import com.example.challengebtg.api.NetworkApi
import com.example.challengebtg.model.Quotation
import com.example.challengebtg.model.QuotesResponse
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RequestGetQuotes(var closure: (response: ArrayList<Quotation>?, error: String?) -> (Unit)) {
    init {
        try {
            init()
        } catch (e: Exception) {
            val error = "GetQuotesError_3: ${e.message}"
            Log.d("Error_Log_D", error)
            closure(null, error)
        }
    }

    private fun init() {
        val observable =
            NetworkApi.public().getQuotes()
        observable.subscribeOn(Schedulers.newThread())
            .subscribe(object : Observer<QuotesResponse> {
                override fun onComplete() {}

                override fun onNext(value: QuotesResponse?) {
                    if (value?.success != true) {
                        val error = "GetQuotesError_1"
                        Log.d("Error_Log_D", error)
                        closure(null, error)
                        return
                    }

                    val quotes = value.quotes.map {
                        Quotation(code = it.key, value = it.value)
                    } as ArrayList

                    closure(quotes, null)
                }

                override fun onError(e: Throwable?) {
                    val error = "GetQuotesError_2: ${e?.message}"
                    Log.d("Error_Log_D", error)
                    closure(null, error)
                }

                override fun onSubscribe(d: Disposable?) {}
            })
    }

}
