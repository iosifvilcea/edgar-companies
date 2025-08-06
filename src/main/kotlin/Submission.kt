package com.blankthings

import kotlinx.serialization.Serializable

@Serializable
data class Submission(
    val cik: Long,
    val entityType: String,
    val sicDescription: String,
    val name: String,
    val tickers: List<String>,
    val exchanges: List<String>,
    val ein: String,
    val filings: Filings
)

@Serializable
data class Filings(
    val recent: Recent
)

@Serializable
data class Recent(
    val filingDate: List<String>,
    val form: List<String>
)