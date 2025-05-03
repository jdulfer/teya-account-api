package com.teya

import com.teya.e2e.utils.configureTestApplication
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        configureTestApplication()
        client.get("/hello").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
