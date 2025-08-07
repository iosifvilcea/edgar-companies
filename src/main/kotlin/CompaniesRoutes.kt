package com.blankthings

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

const val BASE_URL = "https://www.sec.gov/files/company_tickers.json"
val cutoffDate: LocalDate = LocalDate.now().minusDays(548)
val validForms = setOf("10-K", "10-Q", "20-F", "6-K")
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun Route.companiesRoutes() {
    route("companies") {
        get {
            getCompanies()
        }
    }
}

@OptIn(ExperimentalTime::class)
suspend fun getCompanies() {
    val client = HttpClientConfig.client
    val companyTickers: Map<String, CompanyTicker> = client.get(BASE_URL).body()
    println("Total Companies in Edgar: ${companyTickers.values.size}")

    getActiveCompany(client, companyTickers)
}

suspend fun getActiveCompany(client: HttpClient, companyTickers: Map<String, CompanyTicker>) {
    val activeCompanies = mutableListOf<ActiveCompany>()
    companyTickers.forEach { companyTicker ->
        val timeElapsed = measureTime {
            val cikPadded = companyTicker.value.cik.toString().padStart(10, '0')
            val baseSubmissionUrl = "https://data.sec.gov/submissions/CIK${cikPadded}.json"
            val company: ActiveCompany = client.get(baseSubmissionUrl).body()
            val filings = company.filings.recent.form.zip(company.filings.recent.filingDate)

            filings.forEach { filing ->
                if (filing.first in validForms) {
                    val fileDate = LocalDate.parse(filing.second, dateFormatter)
                    if (fileDate.isBefore(cutoffDate).not()) {
                        activeCompanies.add(company)
                        return@forEach
                    }
                }
            }
            delay(100.milliseconds)
        }
        println("Time Elapsed: $timeElapsed")
    }
    println("Final Active Companies Size: ${activeCompanies.size}")
}