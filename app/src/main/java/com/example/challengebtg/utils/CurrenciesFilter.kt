package com.example.challengebtg.utils

import com.example.challengebtg.model.Currency
import com.example.challengebtg.model.Filter

class CurrenciesFilter {
     fun apply(currencies: ArrayList<Currency>,  filter: Filter): ArrayList<Currency> {
        val newList = currencies.filter { currencyFilter(it, filter) } as ArrayList
        newList.sortBy { if(filter.order == "code") it.code else it.name }
        return newList
    }

    private fun currencyFilter(currency: Currency, filter: Filter): Boolean =
        currency.code?.toUpperCase()
            ?.contains(filter.text.toUpperCase()) ?: false || currency.name?.toUpperCase()
            ?.contains(filter.text.toUpperCase()) ?: false

}