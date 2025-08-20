import com.blankthings.ActiveCompany
import com.blankthings.CompanyRepository
import database.ActiveCompanyDao
import database.ActiveCompanyTable
import database.ActiveCompanyTable.ein
import database.ActiveCompanyTable.entityType
import database.ActiveCompanyTable.exchanges
import database.ActiveCompanyTable.name
import database.ActiveCompanyTable.sicDescription
import database.ActiveCompanyTable.tickers
import database.suspendTransaction
import database.toModel
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.v1.core.statements.UpsertSqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.jdbc.batchUpsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.upsert

class CompanyRepositoryImpl: CompanyRepository {
    override suspend fun allActiveCompanies(): List<ActiveCompany> = suspendTransaction {
        ActiveCompanyDao.all().map { it.toModel() }
    }

    override suspend fun addCompany(activeCompany: ActiveCompany) {
        ActiveCompanyDao.new {
            id._value = activeCompany.cik
            entityType = activeCompany.entityType
            sicDescription = activeCompany.sicDescription
            name = activeCompany.name
            tickers = activeCompany.tickers
            exchanges = activeCompany.exchanges
            ein = activeCompany.ein
        }
    }

    override suspend fun addCompanies(activeCompanies: List<ActiveCompany>) {
        suspendTransaction {
            ActiveCompanyTable.batchUpsert(
                activeCompanies,
                keys = arrayOf(ActiveCompanyTable.id)
            ) { activeCompany ->
                this[ActiveCompanyTable.id] = activeCompany.cik
                this[entityType] = activeCompany.entityType
                this[sicDescription] = activeCompany.sicDescription
                this[name] = activeCompany.name
                this[tickers] = activeCompany.tickers
                this[exchanges] = activeCompany.exchanges
                this[ein] = activeCompany.ein
            }
        }
    }

    override suspend fun deleteCompany(activeCompany: ActiveCompany): Boolean = suspendTransaction {
        val rowDeleted = ActiveCompanyTable.deleteWhere {
            ActiveCompanyTable.id eq activeCompany.cik
        }
        rowDeleted == 1
    }

    override suspend fun updateCompany(activeCompany: ActiveCompany) {
        suspendTransaction {
            ActiveCompanyTable.upsert(ActiveCompanyTable.id) {
                it[ActiveCompanyTable.id] = activeCompany.cik
                it[entityType] = activeCompany.entityType
                it[sicDescription] = activeCompany.sicDescription
                it[name] = activeCompany.name
                it[tickers] = activeCompany.tickers
                it[exchanges] = activeCompany.exchanges
                it[ein] = activeCompany.ein
            }
        }
        println("${activeCompany.name} updated to DB.")
    }

    override suspend fun updateCompanies(activeCompanies: List<ActiveCompany>) {
        suspendTransaction {
            val result = ActiveCompanyTable.batchUpsert(
                activeCompanies,
                keys = arrayOf(ActiveCompanyTable.id)
            ) { company ->
                this[ActiveCompanyTable.id] = company.cik
                this[entityType] = company.entityType
                this[sicDescription] = company.sicDescription
                this[name] = company.name
                this[tickers] = company.tickers
                this[exchanges] = company.exchanges
                this[ein] = company.ein
            }

            println("Update Companies Result: $result")

            val numberOfDeletedRows = ActiveCompanyTable.deleteWhere {
                ActiveCompanyTable.id notInList activeCompanies.map { it.cik }
            }
            println("Number Of Deleted Rows: $numberOfDeletedRows")
        }
    }
}