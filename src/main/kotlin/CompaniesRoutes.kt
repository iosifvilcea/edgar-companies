package com.blankthings

import ActiveCompaniesSyncWorker
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import java.lang.Exception

fun Route.companiesRoutes(companyRepository: CompanyRepository) {
    route("/companies") {
        get {
            val activeCompanies = companyRepository.allActiveCompanies()
            activeCompanies.forEach {
                println("" + it.cik + " , " + it.name + " , " + it.sicDescription + " , " + it.entityType)
            }
        }
    }

    // TODO - This route should be secured and not accessible by the public.
    route("/internal") {
        post("/sync-companies") {
            try {
                val syncWorker = ActiveCompaniesSyncWorker(companyRepository = companyRepository)
                syncWorker.syncActiveCompanies()
                call.respond(HttpStatusCode.OK, "Sync completed")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Sync failed: ${e.message} : ${e.printStackTrace()}")
            }
        }
    }
}