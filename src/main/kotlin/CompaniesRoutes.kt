package com.blankthings

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.routing.*
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

// TODO - Move it out
@OptIn(ExperimentalTime::class)
suspend fun getCompanies() {
    val client = HttpClientConfig.client
    val companyTickers: Map<String, CompanyTicker> = client.get(BASE_URL).body()
    println("Total Companies in Edgar: ${companyTickers.values.size}")

    val cikPadded = companyTickers.values.first().cik.toString().padStart(10, '0')
    val baseSubmissionUrl = "https://data.sec.gov/submissions/CIK${cikPadded}.json"
    val submission: Submission = client.get(baseSubmissionUrl).body()
    println("submission: $submission")

    val time = TimeSource.Monotonic.markNow().elapsedNow()
    println("Time Elapsed: $time")
}