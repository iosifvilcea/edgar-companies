package com.blankthings

interface CompanyRepository {
    suspend fun getActiveCompanies(): List<ActiveCompany>
    suspend fun addCompany(activeCompany: ActiveCompany)
    suspend fun addCompanies(activeCompanies: List<ActiveCompany>)
    suspend fun deleteCompany(activeCompany: ActiveCompany)
    suspend fun updateCompanies(activeCompanies: List<ActiveCompany>)
}