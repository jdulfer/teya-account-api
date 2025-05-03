package com.teya.daos.memory

import com.teya.daos.AccountDAO
import com.teya.db.AccountsDB
import com.teya.domain.Account
import io.ktor.server.plugins.*

class MemoryAccountDAO(private val accountsDB: AccountsDB) : AccountDAO {
    override fun getAccount(accountId: String): Account =
        accountsDB.accounts.find { it.accountId == accountId } ?: throw NotFoundException("Account not found")
}