package com.blankthings

import kotlinx.serialization.Serializable

@Serializable
data class ActiveCompany(
    val cik: Long,
    val entityType: String,
    val sicDescription: String,
    val name: String,
    val tickers: List<String>,
    val exchanges: List<String>,
    val ein: String
)

@Serializable
data class CompanyRaw(
    val cik: Long,
    val entityType: String?,
    val sicDescription: String?,
    val name: String?,
    val tickers: List<String?>?,
    val exchanges: List<String?>?,
    val ein: String?,
    val filings: FilingsRaw?
)

@Serializable
data class FilingsRaw(
    val recent: RecentRaw?
)

@Serializable
data class RecentRaw(
    val filingDate: List<String?>?,
    val form: List<String?>?
)

fun CompanyRaw.toActiveCompany() = ActiveCompany(
    cik = cik,
    entityType = entityType.orEmpty(),
    sicDescription = sicDescription.orEmpty(),
    name = name.orEmpty(),
    tickers = tickers.orEmpty().filterNotNull(),
    exchanges = exchanges.orEmpty().filterNotNull(),
    ein = ein.orEmpty()
)

fun ActiveCompany.isValid(): Boolean {
    return entityType.isNotBlank() &&
            sicDescription.isNotBlank() &&
            name.isNotBlank() &&
            tickers.isNotEmpty() &&
            exchanges.isNotEmpty()
}