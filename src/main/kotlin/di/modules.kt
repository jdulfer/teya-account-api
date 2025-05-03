package com.teya.di

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val appModule = module {
    factory<HttpClientEngine> { CIO.create() }
}