package com.example.challengerbtg.utils

import com.example.challengerbtg.model.Currency
import com.example.challengerbtg.model.Quotation
import java.text.DecimalFormat
import java.text.NumberFormat

class Convert {
    fun currency(
        currencyFrom: Currency,
        currencyTo: Currency,
        quotes: ArrayList<Quotation>,
        amount: String
    ): String {
        val quotationFrom = currencyFrom.toQuotation(quotes).value ?: 0.0
        val quotationTo = currencyTo.toQuotation(quotes).value ?: 0.0
        val amountInDolar = amount.toDouble() / quotationFrom

        val formatter: NumberFormat = DecimalFormat("#.##")
        return formatter.format(amountInDolar * quotationTo)
    }
}