package com.blankthings

interface CompanyRepository {
    suspend fun getActiveCompanies(): List<ActiveCompany>
}