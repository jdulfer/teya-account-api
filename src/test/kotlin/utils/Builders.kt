package com.teya.utils

import com.teya.domain.Account
import java.math.BigDecimal

class AccountBuilder {
    var accountId: String = "1"
    var accountName: String = "Alan Turing"
    var balance: BigDecimal = BigDecimal("100")

    fun createAccount(accountBuilder: AccountBuilder.() -> Unit): Account {
        this.apply(accountBuilder)

        return Account(accountId, accountName, balance)
    }
}