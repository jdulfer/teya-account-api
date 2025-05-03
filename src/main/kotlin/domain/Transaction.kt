package com.teya.domain

import java.math.BigDecimal

data class Transaction(
    val transactionId: String,
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val senderAccountId: String,
    val receiverAccountId: String,
) {
    enum class TransactionType {
        INCOMING, OUTGOING
    }
}
