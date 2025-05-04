package com.teya.routes

import com.teya.domain.Transaction
import com.teya.domain.Transaction.TransactionType
import com.teya.services.AccountService
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import java.math.BigDecimal

fun Routing.accountRoutes() {
    val accountService: AccountService by application.inject()

    route("/accounts/{accountId}") {
        get("/balance") {
            val accountId = call.parameters["accountId"] ?: throw NotFoundException("Please provide an account id")
            val accountBalance = accountService.getAccountBalance(accountId).toPlainString()
            call.respond(AccountBalanceResponse(accountBalance))
        }

        get("/transactions") {
            val accountId = call.parameters["accountId"] ?: throw NotFoundException("Please provide an account id")
            val transactions = accountService.getAccountHistory(accountId)
            call.respond(transactions.map { transaction ->
                TransactionResponse(
                    transactionId = transaction.transactionId,
                    transactionType = transaction.transactionType,
                    amount = transaction.amount.setScale(2).toPlainString(),
                    accountId = transaction.accountId,
                    counterpartyId = transaction.counterpartyId,
                    status = transaction.status
                )
            })
        }

        post("/move-money") {
            val accountId = call.parameters["accountId"] ?: throw NotFoundException("Please provide an account id")
            val payload = call.receive<MoveMoneyPayload>()
            accountService.moveMoney(
                accountId,
                payload.receiverAccountId,
                BigDecimal(payload.amount)
            )
            call.respond(HttpStatusCode.Created)
        }
    }
}

@Serializable
data class MoveMoneyPayload(
    val receiverAccountId: String,
    val amount: String
)

@Serializable
data class AccountBalanceResponse(val balance: String)

@Serializable
data class TransactionResponse(
    val transactionId: String,
    val transactionType: TransactionType,
    val amount: String,
    val accountId: String,
    val counterpartyId: String,
    val status: Transaction.TransactionStatus
)