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
            ein = "91-1144442",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-01-15", "2023-10-30", "2023-07-25", "2023-04-20"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 320193,
            entityType = "operating",
            sicDescription = "Electronic Computers",
            name = "Apple Inc.",
            tickers = listOf("AAPL"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "94-2404110",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-02-01", "2023-11-02", "2023-08-03", "2023-05-04"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 1018724,
            entityType = "operating",
            sicDescription = "Retail-Catalog & Mail-Order Houses",
            name = "Amazon.com, Inc.",
            tickers = listOf("AMZN"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "91-1646860",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-01-31", "2023-10-26", "2023-07-27", "2023-04-27"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 1652044,
            entityType = "operating",
            sicDescription = "Services-Computer Programming Services",
            name = "Alphabet Inc.",
            tickers = listOf("GOOGL", "GOOG"),
            exchanges = listOf("Nasdaq Global Select", "Nasdaq Global Select"),
            ein = "61-1767919",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-02-06", "2023-10-24", "2023-07-25", "2023-04-25"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 1318605,
            entityType = "operating",
            sicDescription = "Electric & Other Services Combined",
            name = "Tesla, Inc.",
            tickers = listOf("TSLA"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "91-2197729",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-01-29", "2023-10-23", "2023-07-24", "2023-04-24"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 1559720,
            entityType = "operating",
            sicDescription = "Services-Computer Programming Services",
            name = "Meta Platforms, Inc.",
            tickers = listOf("META"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "20-1665019",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-01-31", "2023-10-25", "2023-07-26", "2023-04-26"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 908732,
            entityType = "operating",
            sicDescription = "Pharmaceutical Preparations",
            name = "Johnson & Johnson",
            tickers = listOf("JNJ"),
            exchanges = listOf("New York Stock Exchange"),
            ein = "22-1024240",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-02-20", "2023-10-17", "2023-07-18", "2023-04-18"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 19617,
            entityType = "operating",
            sicDescription = "Investment Advice",
            name = "JPMorgan Chase & Co.",
            tickers = listOf("JPM"),
            exchanges = listOf("New York Stock Exchange"),
            ein = "13-2624428",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-02-28", "2023-10-13", "2023-07-14", "2023-04-14"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 1467373,
            entityType = "operating",
            sicDescription = "Services-Computer Processing & Data Preparation",
            name = "Netflix, Inc.",
            tickers = listOf("NFLX"),
            exchanges = listOf("Nasdaq Global Select"),
            ein = "77-0467272",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-01-23", "2023-10-19", "2023-07-18", "2023-04-18"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        ),
        ActiveCompany(
            cik = 66740,
            entityType = "operating",
            sicDescription = "Beverages",
            name = "The Coca-Cola Company",
            tickers = listOf("KO"),
            exchanges = listOf("New York Stock Exchange"),
            ein = "58-0628465",
            filings = Filings(
                recent = Recent(
                    filingDate = listOf("2024-02-21", "2023-10-25", "2023-07-25", "2023-04-25"),
                    form = listOf("10-K", "10-Q", "10-Q", "10-Q")
                )
            )
        )
    )

    override suspend fun allActiveCompanies(): List<ActiveCompany> = activeCompanies
}