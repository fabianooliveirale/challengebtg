package com.example.challengerbtg.utils

import com.example.challengerbtg.model.Currency

class Filter {
    fun currencies(currencies: ArrayList<Currency>, text: String = ""): ArrayList<Currency> {
        return currencies.filter { currencyFilter(it, text) } as ArrayList
    }

    private fun currencyFilter(currency: Currency, text: String = ""): Boolean =
        currency.code?.toUpperCase()
            ?.contains(text.toUpperCase()) ?: false || currency.name?.toUpperCase()
            ?.contains(text.toUpperCase()) ?: false


}