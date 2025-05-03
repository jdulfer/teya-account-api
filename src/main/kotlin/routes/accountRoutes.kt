package com.teya.routes

import com.teya.services.AccountService
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Routing.accountRoutes() {
    val accountService: AccountService by application.inject()

    route("/account/{accountId}") {
        get("/balance") {
            val accountId = call.parameters["accountId"] ?: throw NotFoundException("Please provide an account id")
            val accountBalance = accountService.getAccountBalance(accountId).toPlainString()
            call.respond(AccountBalanceResponse(accountBalance))
        }
    }
}

@Serializable
data class AccountBalanceResponse(val balance: String)