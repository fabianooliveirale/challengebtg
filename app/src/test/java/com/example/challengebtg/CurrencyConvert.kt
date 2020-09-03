package com.example.challengebtg

import com.example.challengebtg.model.Currency
import com.example.challengebtg.model.Quotation
import com.example.challengebtg.utils.Convert
import org.junit.Assert
import org.junit.Test

class CurrencyConvert {
    val quotes = arrayListOf(
        Quotation(code = "USDUSD", value = 1.0),
        Quotation(code = "USDBRL", value = 5.364011)
    )

    @Test
    fun `USD to BRL`() {
        val selectedCurrencyFrom = Currency(code = "USD")
        val selectedCurrencyTo = Currency(code = "BRL")
        val amount = 1
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount.toString())
        val espected = "5,36"
        Assert.assertEquals(convertAmount, espected)
    }


    @Test
    fun `USD to BRL 2`() {
        val selectedCurrencyFrom = Currency(code = "USD")
        val selectedCurrencyTo = Currency(code = "BRL")
        val amount = 2
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount.toString())
        val espected = "10,73"
        Assert.assertEquals(convertAmount, espected)
    }


    @Test
    fun `BRL to BRL`() {
        val selectedCurrencyFrom = Currency(code = "BRL")
        val selectedCurrencyTo = Currency(code = "BRL")
        val amount = 1
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount.toString())
        val espected = "1"
        Assert.assertEquals(convertAmount, espected)
    }

    @Test
    fun `USD to USD`() {
        val selectedCurrencyFrom = Currency(code = "USD")
        val selectedCurrencyTo = Currency(code = "USD")
        val amount = 1
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount.toString())
        val espected = "1"
        Assert.assertEquals(convertAmount, espected)
    }
}