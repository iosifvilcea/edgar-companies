package database

import com.blankthings.ActiveCompany
import com.blankthings.Filings
import com.blankthings.Recent
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

const val MAX_ENTITY_TYPE_LENGTH = 50
const val MAX_NAME_LENGTH = 255
const val MAX_EIN_LENGTH = 20

object ActiveCompanyTable: LongIdTable("active_companies", "cik") {
    val entityType = varchar("entity_type", MAX_ENTITY_TYPE_LENGTH)
    val sicDescription = text("sic_description")
    val name = varchar("name", MAX_NAME_LENGTH)
    val tickers = array<String>("tickers")
    val exchanges = array<String>("exchanges")
    val ein = varchar("ein", MAX_EIN_LENGTH).nullable()
    val fillingDates = array<String>("filing_dates")
    val fillingForms = array<String>("filing_forms")
}

class ActiveCompanyDao(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<ActiveCompanyDao>(ActiveCompanyTable)
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

fun ActiveCompanyDao.toModel() = ActiveCompany(
    cik = id.value,
    entityType = entityType,
    sicDescription = sicDescription,
    name = name,
    tickers = tickers,
    exchanges = exchanges,
    ein = ein,
    filings = Filings(
        Recent(
            filingDate = fillingDates,
            form = fillingForms
        )
    )
)