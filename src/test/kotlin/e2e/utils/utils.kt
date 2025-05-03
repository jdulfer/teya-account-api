package com.teya.e2e.utils

import com.teya.config.module
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun ApplicationTestBuilder.configureTestApplication(mockEngine: MockEngine? = null) {
    application {
        module()

        val defaultMockEngine = MockEngine {
            respond(
                content = "OK",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        loadKoinModules(module {
            factory<HttpClientEngine> { mockEngine ?: defaultMockEngine }
        })
    }
}