package com.teya.daos.http

import com.teya.clients.TeyaClientFactory
import com.teya.daos.TransactionDAO
import com.teya.domain.Transaction
import com.teya.domain.Transaction.TransactionType
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import java.math.BigDecimal

class HttpTransactionDAO(private val engine: HttpClientEngine) : TransactionDAO {
    override suspend fun getTransactions(accountId: String): List<Transaction> {
        val client = TeyaClientFactory.createClient(engine)
        val response = client.get("http://localhost:8081") {
            url {
                path("accounts", accountId, "transactions")
            }
        }

        return response.body<List<TransactionDTO>>().map { it.toDomain() }
    }

    override suspend fun moveMoney(
        senderAccountId: String,
        receiverAccountId: String,
        amount: BigDecimal
    ): List<Transaction> {
        val client = TeyaClientFactory.createClient(engine)
        val response = client.post("http://localhost:8081") {
            url {
                path("transactions", "move-money")
            }
            contentType(ContentType.Application.Json)
            setBody(
                MoveMoneyPayload(senderAccountId, receiverAccountId, amount.setScale(2).toPlainString())
            )
        }

        return response.body<List<TransactionDTO>>().map { it.toDomain() }
    }

    override suspend fun reverseTransactions(transactions: List<Transaction>): List<Transaction> {
        val client = TeyaClientFactory.createClient(engine)
        val response = client.post("http://localhost:8081") {
            url {
                path("transactions", "reverse")
            }
            contentType(ContentType.Application.Json)
            setBody(transactions.map { ReversalPayload(it.transactionId) })
        }

        val reversedTransactionsDTO: List<TransactionDTO> = response.body()
        return reversedTransactionsDTO.map { it.toDomain() }
    }

    @Serializable
    data class ReversalPayload(
        val transactionId: String
    )

    @Serializable
    data class MoveMoneyPayload(
        val senderAccountId: String,
        val receiverAccountId: String,
        val amount: String
    )

    @Serializable
    data class TransactionDTO(
        val transactionId: String,
        val transactionType: TransactionType,
        val amount: String,
        val accountId: String,
        val counterpartyId: String,
        val status: Transaction.TransactionStatus
    ) {
        fun toDomain() = Transaction(
            transactionId = transactionId,
            transactionType = transactionType,
            amount = BigDecimal(amount).setScale(2),
            accountId = accountId,
            counterpartyId = counterpartyId,
            status = status
        )
    }
}