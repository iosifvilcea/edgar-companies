package com.blankthings

import io.ktor.server.routing.*

fun Route.companiesRoutes(companyRepository: CompanyRepository) {
    route("companies") {
        get {
            val activeCompanies = companyRepository.getActiveCompanies()
            println("Fetched ActiveCompanies: $activeCompanies")
        }
    }
}