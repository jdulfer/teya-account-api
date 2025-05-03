package com.teya.daos

import com.teya.domain.Transaction
import java.math.BigDecimal

interface TransactionDAO {
    fun getTransactions(accountId: String): List<Transaction>
    fun moveMoney(senderAccountId: String, receiverAccountId: String, amount: BigDecimal): List<Transaction>
    fun reverseTransactions(transactions: List<Transaction>): List<Transaction>
}