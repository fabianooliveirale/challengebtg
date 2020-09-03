package com.example.challengebtg

import com.example.challengebtg.model.Currency
import com.example.challengebtg.model.Quotation
import com.example.challengebtg.utils.Convert
import org.junit.Assert
import org.junit.Test

class CurrencyConvertTest {
    private val quotes = arrayListOf(
        Quotation(code = "USDUSD", value = 1.0),
        Quotation(code = "USDBRL", value = 5.364011)
    )

    @Test
    fun `USD to BRL`() {
        val selectedCurrencyFrom = Currency(code = "USD")
        val selectedCurrencyTo = Currency(code = "BRL")
        val amount = 1.0
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount)
        val expected = "5,36"
        Assert.assertEquals(convertAmount, expected)
    }


    @Test
    fun `USD to BRL 2`() {
        val selectedCurrencyFrom = Currency(code = "USD")
        val selectedCurrencyTo = Currency(code = "BRL")
        val amount = 2.0
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount)
        val expected = "10,73"
        Assert.assertEquals(convertAmount, expected)
    }


    @Test
    fun `BRL to BRL`() {
        val selectedCurrencyFrom = Currency(code = "BRL")
        val selectedCurrencyTo = Currency(code = "BRL")
        val amount = 1.0
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount)
        val expected = "1"
        Assert.assertEquals(convertAmount, expected)
    }

    @Test
    fun `USD to USD`() {
        val selectedCurrencyFrom = Currency(code = "USD")
        val selectedCurrencyTo = Currency(code = "USD")
        val amount = 1.0
        val convertAmount =
            Convert().currency(selectedCurrencyFrom, selectedCurrencyTo, quotes, amount)
        val expected = "1"
        Assert.assertEquals(convertAmount, expected)
    }
}