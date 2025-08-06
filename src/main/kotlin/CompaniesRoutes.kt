package com.blankthings

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

const val BASE_URL = "https://www.sec.gov/files/company_tickers.json"

fun Route.companiesRoutes() {
    route("companies") {
        get {
            getCompanies()
        }
    }
}

// TODO - Move it out
suspend fun getCompanies() {
    val client = HttpClientConfig.client
    val body: Map<String, CompanyTicker> = client.get(BASE_URL).body()
    println("Total Companies in Edgar: ${body.values.size}")
}