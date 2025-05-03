package com.teya.domain

import java.math.BigDecimal

data class Transaction(
    val transactionId: String,
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val accountId: String,
    val counterpartyId: String,
) {
    enum class TransactionType {
        INCOMING, OUTGOING
    }
}
