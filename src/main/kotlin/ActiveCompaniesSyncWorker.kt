import com.blankthings.ActiveCompany
import com.blankthings.CompanyRaw
import com.blankthings.CompanyRepository
import com.blankthings.CompanyTicker
import com.blankthings.HttpClientConfig
import com.blankthings.isValid
import com.blankthings.toActiveCompany
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

class ActiveCompaniesSyncWorker(
    val client: HttpClient = HttpClientConfig.client,
    val companyRepository: CompanyRepository
) {
    val baseUrl = "https://www.sec.gov/files/company_tickers.json"
    val cutoffDate: LocalDate = LocalDate.now().minusDays(548)
    val validForms = setOf("10-K", "10-Q", "20-F", "6-K")
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    suspend fun syncActiveCompanies() {
        val companyTickers = getCompaniesTicker()
        val activeCompanies = getActiveCompany(companyTickers)
        companyRepository.updateCompanies(activeCompanies)
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun getCompaniesTicker(): Map<String, CompanyTicker>  {
        val companyTickers: Map<String, CompanyTicker> = client.get(baseUrl).body()
        println("Total Companies in Edgar: ${companyTickers.values.size}")
        return companyTickers
    }

    suspend fun getActiveCompany(companyTickers: Map<String, CompanyTicker>): List<ActiveCompany> {
        val activeCompanies = mutableListOf<ActiveCompany>()
        companyTickers.forEach { companyTicker ->
            val cikPadded = companyTicker.value.cik.toString().padStart(10, '0')
            val baseSubmissionUrl = "https://data.sec.gov/submissions/CIK${cikPadded}.json"
            val companyRawData: CompanyRaw = client.get(baseSubmissionUrl).body()

            val company = companyRawData.toActiveCompany()
            if (company.isValid()) {
                val filings = company.filings.recent.form.zip(company.filings.recent.filingDate)
                val hasRecentValidFilings = filings.any { filing ->
                    val filingDate = LocalDate.parse(filing.second, dateFormatter)
                    filing.first in validForms && filingDate.isBefore(cutoffDate).not()
                }

                if (hasRecentValidFilings) {
                    activeCompanies.add(company)
                }
            }

            delay(100.milliseconds)
        }
        println("Final Active Companies Size: ${activeCompanies.size}")
        return activeCompanies
    }
}