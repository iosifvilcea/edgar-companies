package com.blankthings

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.routing.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

const val SUBMISSIONS_RATE_LIMIT = "company-submissions"
const val BASE_URL = "https://www.sec.gov/files/company_tickers.json"

fun Route.companiesRoutes() {
    route("companies") {
        rateLimit(RateLimitName(SUBMISSIONS_RATE_LIMIT)) {
            get {
                getCompanies()
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
suspend fun getCompanies() {
    val client = HttpClientConfig.client
    val companyTickers: Map<String, CompanyTicker> = client.get(BASE_URL).body()
    println("Total Companies in Edgar: ${companyTickers.values.size}")

    val cikPadded = companyTickers.values.first().cik.toString().padStart(10, '0')
    val baseSubmissionUrl = "https://data.sec.gov/submissions/CIK${cikPadded}.json"
    val company: ActiveCompany = client.get(baseSubmissionUrl).body()
    println("submission: $company")

    val time = TimeSource.Monotonic.markNow().elapsedNow()
    println("Time Elapsed: $time")

    val cutoffDate = LocalDate.now().minusDays(548)
    val validForms = setOf("10-K", "10-Q", "20-F", "6-K")
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var activeCompany: ActiveCompany? = null
    val filings = company.filings.recent.form.zip(company.filings.recent.filingDate)

    filings.forEach { filing ->
        if (filing.first in validForms) {
            val fileDate = LocalDate.parse(filing.second, dateFormatter)
            if (fileDate.isBefore(cutoffDate).not()) {
                activeCompany = company.copy()
                return@forEach
            }
        }
    }

    println("This company is active: $activeCompany")
    val finalTime = TimeSource.Monotonic.markNow().elapsedNow()
    println("Final Time Elapsed: $finalTime")
}