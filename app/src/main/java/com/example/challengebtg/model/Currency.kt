package com.example.challengebtg.model

data class Currency(
    var code: String? = null,
    var name: String? = null
) {
    override fun toString(): String {
        return this.code ?: ""
    }

    fun toQuotation(quotes: ArrayList<Quotation>): Quotation =
        quotes.first { "USD" + this.code == it.code }
}