import com.blankthings.ActiveCompany
import com.blankthings.CompanyRepository
import com.blankthings.HttpClientConfig
import io.ktor.client.*

class CompanyRepositoryImpl(
    val client: HttpClient = HttpClientConfig.client
): CompanyRepository {
    override suspend fun getActiveCompanies(): List<ActiveCompany> {
        TODO("Not yet implemented")
    }

    override suspend fun addCompany(activeCompany: ActiveCompany) {
        TODO("Not yet implemented")
    }

    override suspend fun addCompanies(activeCompanies: List<ActiveCompany>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompany(activeCompany: ActiveCompany) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCompanies(activeCompanies: List<ActiveCompany>) {
        TODO("Not yet implemented")
    }
}