package com.teya.daos.memory

import com.teya.daos.AccountDAO
import com.teya.db.getAccounts
import com.teya.domain.Account
import io.ktor.server.plugins.*

class MemoryAccountDAO : AccountDAO {
    override fun getAccount(accountId: String): Account =
        getAccounts().find { it.accountId == accountId } ?: throw NotFoundException("Account not found")
}