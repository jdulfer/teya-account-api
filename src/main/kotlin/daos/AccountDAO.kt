package com.teya.daos

import com.teya.domain.Account
import java.math.BigDecimal

interface AccountDAO {
    fun getAccount(accountId: String): Account
    fun changeAccountBalances(balanceUpdates: List<Pair<String, BigDecimal>>): List<Account>
}