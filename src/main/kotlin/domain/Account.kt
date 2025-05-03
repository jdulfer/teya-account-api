package com.teya.domain

import java.math.BigDecimal

data class Account(
    val accountId: String,
    val accountName: String,
    var balance: BigDecimal
)
