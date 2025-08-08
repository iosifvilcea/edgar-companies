import com.blankthings.ActiveCompany
import com.blankthings.CompanyRepository
import com.blankthings.CompanyTicker
import com.blankthings.HttpClientConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.forEach
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class CompanyRepositoryImpl(
    val client: HttpClient = HttpClientConfig.client
): CompanyRepository {

    val baseUrl = "https://www.sec.gov/files/company_tickers.json"
    val cutoffDate: LocalDate = LocalDate.now().minusDays(548)
    val validForms = setOf("10-K", "10-Q", "20-F", "6-K")
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override suspend fun getActiveCompanies(): List<ActiveCompany> {
        val companyTickers = getCompaniesTicker()
        return getActiveCompany(companyTickers)
    }

    @OptIn(ExperimentalTime::class)
    suspend fun getCompaniesTicker(): Map<String, CompanyTicker>  {
        val companyTickers: Map<String, CompanyTicker> = client.get(baseUrl).body()
        println("Total Companies in Edgar: ${companyTickers.values.size}")
        return companyTickers
    }

    suspend fun getActiveCompany(companyTickers: Map<String, CompanyTicker>): List<ActiveCompany> {
        val activeCompanies = mutableListOf<ActiveCompany>()
        companyTickers.forEach { companyTicker ->
            val timeElapsed = measureTime {
                val cikPadded = companyTicker.value.cik.toString().padStart(10, '0')
                val baseSubmissionUrl = "https://data.sec.gov/submissions/CIK${cikPadded}.json"
                val company: ActiveCompany = client.get(baseSubmissionUrl).body()
                val filings = company.filings.recent.form.zip(company.filings.recent.filingDate)

                filings.forEach { filing ->
                    if (filing.first in validForms) {
                        val fileDate = LocalDate.parse(filing.second, dateFormatter)
                        if (fileDate.isBefore(cutoffDate).not()) {
                            activeCompanies.add(company)
                            return@forEach
                        }
                    }
                }
                delay(100.milliseconds)
            }
            println("Time Elapsed: $timeElapsed")
        }
        println("Final Active Companies Size: ${activeCompanies.size}")
        return activeCompanies
    }

}