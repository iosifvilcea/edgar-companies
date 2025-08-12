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
    val ein: String,
    val filings: Filings
)

@Serializable
data class Filings(
    val recent: Recent
) {
    companion object {
        fun empty() = Filings(
            Recent(
                filingDate = mutableListOf(),
                form = mutableListOf()
            )
        )
    }
}

@Serializable
data class Recent(
    val filingDate: List<String>,
    val form: List<String>
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
    ein = ein.orEmpty(),
    filings = filings?.toFilings() ?: Filings.empty()
)

fun FilingsRaw.toFilings() = Filings(
    recent = Recent(
        filingDate = this.recent?.filingDate?.filterNotNull() ?: emptyList(),
        form = this.recent?.form?.filterNotNull() ?: emptyList()
    )
)

fun ActiveCompany.isValid(): Boolean {
    return entityType.isNotBlank() &&
            sicDescription.isNotBlank() &&
            name.isNotBlank() &&
            tickers.isNotEmpty() &&
            exchanges.isNotEmpty() &&
            filings.recent.form.isNotEmpty() &&
            filings.recent.filingDate.isNotEmpty()
}