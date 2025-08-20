package com.blankthings

interface CompanyRepository {
    suspend fun allActiveCompanies(): List<ActiveCompany>
    suspend fun addCompany(activeCompany: ActiveCompany)
    suspend fun addCompanies(activeCompanies: List<ActiveCompany>)
    suspend fun deleteCompany(activeCompany: ActiveCompany): Boolean
    suspend fun updateCompany(activeCompany: ActiveCompany)
    suspend fun updateCompanies(activeCompanies: List<ActiveCompany>)
}