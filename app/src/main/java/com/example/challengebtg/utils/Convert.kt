package com.example.challengebtg.utils

import com.example.challengebtg.model.Currency
import com.example.challengebtg.model.Quotation
import java.text.DecimalFormat
import java.text.NumberFormat

class Convert {
    fun currency(
        currencyFrom: Currency,
        currencyTo: Currency,
        quotes: ArrayList<Quotation>,
        amount: Double
    ): String? {
        val quotationFrom = currencyFrom.toQuotation(quotes).value ?: 0.0
        val quotationTo = currencyTo.toQuotation(quotes).value ?: 0.0
        val amountInDolar = amount / quotationFrom

        val formatter: NumberFormat = DecimalFormat("#.##")
        return formatter.format(amountInDolar * quotationTo)
    }
}