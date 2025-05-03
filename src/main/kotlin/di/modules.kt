package com.teya.di

import com.teya.daos.AccountDAO
import com.teya.daos.TransactionDAO
import com.teya.daos.http.HttpTransactionDAO
import com.teya.daos.memory.MemoryAccountDAO
import com.teya.services.AccountService
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val appModule = module {
    single<AccountDAO> { MemoryAccountDAO() }
    single<TransactionDAO> { HttpTransactionDAO() }

    single { AccountService(get(), get()) }

    factory<HttpClientEngine> { CIO.create() }
}