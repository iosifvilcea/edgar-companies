package database

import io.ktor.server.application.*
import org.jetbrains.exposed.v1.jdbc.Database

// TODO - Add DAO for our ActiveCompany Model
// TODO - Add Service to store / retrieve data from postgresql DB
// TODO - Probably hide the DB credentials somewhere

fun Application.configureDatabases() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/edgar_companies_db",
        user = "postgres",
        password = "password"
    )
}