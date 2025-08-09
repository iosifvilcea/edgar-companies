import com.blankthings.ActiveCompany
import com.blankthings.CompanyRepository
import com.blankthings.HttpClientConfig
import database.ActiveCompanyDao
import database.ActiveCompanyTable
import database.ActiveCompanyTable.cik
import database.ActiveCompanyTable.ein
import database.ActiveCompanyTable.entityType
import database.ActiveCompanyTable.exchanges
import database.ActiveCompanyTable.fillingDates
import database.ActiveCompanyTable.fillingForms
import database.ActiveCompanyTable.name
import database.ActiveCompanyTable.sicDescription
import database.ActiveCompanyTable.tickers
import database.daoToModel
import database.suspendTransaction
import io.ktor.client.*
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.v1.core.statements.UpsertSqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.batchUpsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere

class CompanyRepositoryImpl(
    val client: HttpClient = HttpClientConfig.client
): CompanyRepository {
    override suspend fun allActiveCompanies(): List<ActiveCompany> = suspendTransaction {
        ActiveCompanyDao.all().map(::daoToModel)
    }

    override suspend fun addCompany(activeCompany: ActiveCompany) {
        ActiveCompanyDao.new {
            cik = activeCompany.cik
            entityType = activeCompany.entityType
            sicDescription = activeCompany.sicDescription
            name = activeCompany.name
            tickers = activeCompany.tickers
            exchanges = activeCompany.exchanges
            ein = activeCompany.ein.orEmpty()
            fillingDates = activeCompany.filings.recent.filingDate
            fillingForms = activeCompany.filings.recent.form
        }
    }

    override suspend fun addCompanies(activeCompanies: List<ActiveCompany>) {
        suspendTransaction {
            ActiveCompanyTable.batchInsert(activeCompanies) { activeCompany ->
                this[cik] = activeCompany.cik
                this[entityType] = activeCompany.entityType
                this[sicDescription] = activeCompany.sicDescription
                this[name] = activeCompany.name
                this[tickers] = activeCompany.tickers
                this[exchanges] = activeCompany.exchanges
                this[ein] = activeCompany.ein.orEmpty()
                this[fillingDates] = activeCompany.filings.recent.filingDate
                this[fillingForms] = activeCompany.filings.recent.form
            }
        }
    }

    override suspend fun deleteCompany(activeCompany: ActiveCompany): Boolean = suspendTransaction {
        val rowDeleted = ActiveCompanyTable.deleteWhere {
            ActiveCompanyTable.cik eq activeCompany.cik
        }
        rowDeleted == 1
    }

    override suspend fun updateCompanies(activeCompanies: List<ActiveCompany>) {
        suspendTransaction {
            ActiveCompanyTable.batchUpsert(activeCompanies, ActiveCompanyTable.cik) { company ->
                this[cik] = company.cik
                this[entityType] = company.entityType
                this[sicDescription] = company.sicDescription
                this[name] = company.name
                this[tickers] = company.tickers
                this[exchanges] = company.exchanges
                this[ein] = company.ein.orEmpty()
                this[fillingDates] = company.filings.recent.filingDate
                this[fillingForms] = company.filings.recent.form
            }

            ActiveCompanyTable.deleteWhere {
                ActiveCompanyTable.cik notInList activeCompanies.map { it.cik }
            }
        }
    }
}