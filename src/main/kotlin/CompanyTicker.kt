package com.blankthings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyTicker(
    @SerialName("cik_str")
    val cik: Long,
    val ticker: String,
    val title: String
)
