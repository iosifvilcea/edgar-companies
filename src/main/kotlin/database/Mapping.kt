package database

import com.blankthings.ActiveCompany
import com.blankthings.Filings
import com.blankthings.Recent
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.timestampWithTimeZone
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction


object ActiveCompanyTable: IntIdTable("active_companies") {
    val cik = long("cik")
    val entityType = varchar("entity_type", 50)
    val sicDescription = text("sic_description")
    val name = varchar("name", 255)
    val tickers = array<String>("tickers")
    val exchanges = array<String>("exchanges")
    val ein = varchar("ein", 20)
    val fillingDates = array<String>("filing_dates")
    val fillingForms = array<String>("filing_forms")
    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at")
}

class ActiveCompanyDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ActiveCompanyDao>(ActiveCompanyTable)
    var cik by ActiveCompanyTable.cik
    var entityType by ActiveCompanyTable.entityType
    var sicDescription by ActiveCompanyTable.sicDescription
    var name by ActiveCompanyTable.name
    var tickers by ActiveCompanyTable.tickers
    var exchanges by ActiveCompanyTable.exchanges
    var ein by ActiveCompanyTable.ein
    var fillingDates by ActiveCompanyTable.fillingDates
    var fillingForms by ActiveCompanyTable.fillingForms
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: ActiveCompanyDao) = ActiveCompany(
    cik = dao.cik,
    entityType = dao.entityType,
    sicDescription = dao.sicDescription,
    name = dao.name,
    tickers = dao.tickers,
    exchanges = dao.exchanges,
    ein = dao.ein,
    filings = Filings(
        Recent(
            dao.fillingDates,
            dao.fillingForms
        )
    )
)