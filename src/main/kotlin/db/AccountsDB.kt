package com.teya.db

import com.teya.domain.Account
import java.math.BigDecimal

fun getAccounts(): List<Account> = listOf(
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
        accountName = "Taravangian",
        balance = BigDecimal("8753")
    ),
    Account(
        accountId = "5",
        accountName = "Jasnah",
        balance = BigDecimal("15438")
    )
)