import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

// TODO - Add DAO for our ActiveCompany Model
// TODO - Add Service to store / retrieve data from postgresql DB

fun Application.configureDatabases() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/edgar_companies_db",
        user = "postgres",
        password = "password"
    )
}