package com.teya.domain

import java.math.BigDecimal

data class Transaction(
    val transactionId: String,
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val accountId: String,
    val counterpartyId: String,
    val status: TransactionStatus
) {
    enum class TransactionType {
        INCOMING, OUTGOING
    }

    enum class TransactionStatus {
        PENDING, COMPLETE, REVERSAL, FAILED
    }
}
