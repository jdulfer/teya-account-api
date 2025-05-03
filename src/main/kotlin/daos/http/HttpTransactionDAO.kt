package com.teya.daos.http

import com.teya.daos.TransactionDAO
import com.teya.domain.Transaction
import java.math.BigDecimal

class HttpTransactionDAO : TransactionDAO {
    override fun getTransactions(accountId: String): List<Transaction> {
        TODO("Not yet implemented")
    }

    override fun moveMoney(senderAccountId: String, receiverAccountId: String, amount: BigDecimal): List<Transaction> {
        TODO("Not yet implemented")
    }

    override fun reverseTransactions(transactions: List<Transaction>): List<Transaction> {
        TODO("Not yet implemented")
    }
}