package com.blankthings

class FakeCompanyRepositoryImpl: CompanyRepository {
    val activeCompanies = listOf(
        ActiveCompany(
            cik = 789019,
            entityType = "operating",
            sicDescription = "Computer Programming, Data Processing, etc.",
            name = "Microsoft Corporation",
            tickers = listOf("MSFT"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "91-1144442"
        ),
        ActiveCompany(
            cik = 320193,
            entityType = "operating",
            sicDescription = "Electronic Computers",
            name = "Apple Inc.",
            tickers = listOf("AAPL"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "94-2404110"
        ),
        ActiveCompany(
            cik = 1018724,
            entityType = "operating",
            sicDescription = "Retail-Catalog & Mail-Order Houses",
            name = "Amazon.com, Inc.",
            tickers = listOf("AMZN"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "91-1646860"
        ),
        ActiveCompany(
            cik = 1652044,
            entityType = "operating",
            sicDescription = "Services-Computer Programming Services",
            name = "Alphabet Inc.",
            tickers = listOf("GOOGL", "GOOG"),
            exchanges = listOf("Nasdaq Global Select", "Nasdaq Global Select"),
            ein = "61-1767919"
        ),
        ActiveCompany(
            cik = 1318605,
            entityType = "operating",
            sicDescription = "Electric & Other Services Combined",
            name = "Tesla, Inc.",
            tickers = listOf("TSLA"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "91-2197729"
        ),
        ActiveCompany(
            cik = 1559720,
            entityType = "operating",
            sicDescription = "Services-Computer Programming Services",
            name = "Meta Platforms, Inc.",
            tickers = listOf("META"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "20-1665019"
        ),
        ActiveCompany(
            cik = 908732,
            entityType = "operating",
            sicDescription = "Pharmaceutical Preparations",
            name = "Johnson & Johnson",
            tickers = listOf("JNJ"),
            exchanges = listOf("New York Stock Exchange"),
            ein = "22-1024240"
        ),
        ActiveCompany(
            cik = 19617,
            entityType = "operating",
            sicDescription = "Investment Advice",
            name = "JPMorgan Chase & Co.",
            tickers = listOf("JPM"),
            exchanges = listOf("New York Stock Exchange"),
            ein = "13-2624428"
        ),
        ActiveCompany(
            cik = 1467373,
            entityType = "operating",
            sicDescription = "Services-Computer Processing & Data Preparation",
            name = "Netflix, Inc.",
            tickers = listOf("NFLX"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "77-0467272"
        ),
        ActiveCompany(
            cik = 66740,
            entityType = "operating",
            sicDescription = "Beverages",
            name = "The Coca-Cola Company",
            tickers = listOf("KO"),
            exchanges = listOf("New York Stock Exchange"),
            ein = "58-0628465"
        )
    )

    override suspend fun allActiveCompanies(): List<ActiveCompany> = activeCompanies
    override suspend fun addCompany(activeCompany: ActiveCompany) {
        TODO("Not yet implemented")
    }

    override suspend fun addCompanies(activeCompanies: List<ActiveCompany>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompany(activeCompany: ActiveCompany): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateCompanies(activeCompanies: List<ActiveCompany>) {
        TODO("Not yet implemented")
    }
}