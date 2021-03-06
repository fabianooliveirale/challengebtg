package com.example.challengebtg.model

import com.google.gson.annotations.SerializedName

data class QuotesResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("quotes")
    val quotes: Map<String, Double>
)