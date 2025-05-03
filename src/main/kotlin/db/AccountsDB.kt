package com.teya.db

import com.teya.domain.Account
import java.math.BigDecimal

class AccountsDB {
    val accounts = getAccounts()
}

fun getAccounts() = mutableListOf(
    Account(
        accountId = "1",
        accountName = "Kaladin",
        balance = BigDecimal("12")
    ),
    Account(
        accountId = "2",
        accountName = "Dalinar",
        balance = BigDecimal("10000")
    ),
    Account(
        accountId = "3",
        accountName = "Adolin",
        balance = BigDecimal("4023")
    ),
    Account(
        accountId = "4",
        accountName = "Shallan",
        balance = BigDecimal("1")
    ),
    Account(
        accountId = "5",
        accountName = "Jasnah",
        balance = BigDecimal("15438")
    )
)