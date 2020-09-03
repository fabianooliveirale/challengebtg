package com.example.challengebtg

import com.example.challengebtg.model.Currency
import com.example.challengebtg.model.Filter
import com.example.challengebtg.utils.CurrenciesFilter
import org.junit.Assert
import org.junit.Test

class FilterTest {
    private val currencies = arrayListOf(
        Currency(code = "USD", name = "United States Dollar"),
        Currency(code = "BRL", name = "Brazilian Real"),
        Currency(code = "EUR", name = "Euro"),
        Currency(code = "DZD", name = "Algerian Dinar")
    )


    @Test
    fun `Filter USD`() {
        val filter = Filter(text = "USD")
        val expected = arrayListOf(
            Currency(code = "USD", name = "United States Dollar")
        )

        val result = CurrenciesFilter().apply(currencies, filter)

        Assert.assertEquals(result, expected)
    }

    @Test
    fun `Filter BRL`() {
        val filter = Filter(text = "BRL")
        val expected = arrayListOf(
            Currency(code = "BRL", name = "Brazilian Real")
        )

        val result = CurrenciesFilter().apply(currencies, filter)

        Assert.assertEquals(result, expected)
    }

    @Test
    fun `Filter EUR`() {
        val filter = Filter(text = "EUR")
        val expected = arrayListOf(
            Currency(code = "EUR", name = "Euro")
        )

        val result = CurrenciesFilter().apply(currencies, filter)

        Assert.assertEquals(result, expected)
    }

    @Test
    fun `Filter Order Name`() {
        val filter = Filter(order = "name")
        val expected = arrayListOf(
            Currency(code = "DZD", name = "Algerian Dinar"),
            Currency(code = "BRL", name = "Brazilian Real"),
            Currency(code = "EUR", name = "Euro"),
            Currency(code = "USD", name = "United States Dollar")
        )

        val result = CurrenciesFilter().apply(currencies, filter)

        Assert.assertEquals(result, expected)
    }

    @Test
    fun `Filter Order Code`() {
        val filter = Filter(order = "code")

        val expected = arrayListOf(
            Currency(code = "BRL", name = "Brazilian Real"),
            Currency(code = "DZD", name = "Algerian Dinar"),
            Currency(code = "EUR", name = "Euro"),
            Currency(code = "USD", name = "United States Dollar")
        )

        val result = CurrenciesFilter().apply(currencies, filter)

        Assert.assertEquals(result, expected)
    }

    @Test
    fun `Filter Unordered`() {
        val filter = Filter(order = "code")

        val result = CurrenciesFilter().apply(currencies, filter)

        Assert.assertNotEquals(result, currencies)
    }

}