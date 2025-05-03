package com.teya.daos

import com.teya.domain.Transaction
import java.math.BigDecimal

interface TransactionDAO {
    suspend fun getTransactions(accountId: String): List<Transaction>
    suspend fun moveMoney(senderAccountId: String, receiverAccountId: String, amount: BigDecimal): List<Transaction>
    suspend fun reverseTransactions(transactions: List<Transaction>): List<Transaction>
}