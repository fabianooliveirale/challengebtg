package com.example.challengerbtg.model

data class Quotation(
    var code: String? = null,
    var value: Double? = null
) {
    override fun toString(): String {
        return this.code ?: ""
    }
}