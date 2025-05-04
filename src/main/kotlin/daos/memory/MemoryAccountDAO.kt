package com.teya.daos.memory

import com.teya.daos.AccountDAO
import com.teya.db.AccountsDB
import com.teya.domain.Account
import io.ktor.server.plugins.*
import java.math.BigDecimal

class MemoryAccountDAO(private val accountsDB: AccountsDB) : AccountDAO {
    override fun getAccount(accountId: String): Account =
        accountsDB.accounts.find { it.accountId == accountId } ?: throw NotFoundException("Account not found")


    // Just as a noe, this is very unnecessary for a memory solution as I can just manually change the balance on the
    // account and pass it in directly from the service layer, but I'm trying to emulate what it would be like if I had
    // a proper DB sitting underneath this microservice
    override fun changeAccountBalances(balanceUpdates: List<Pair<String, BigDecimal>>): List<Account> {
        return balanceUpdates.map { (accountId, amount) ->
            val account =
                accountsDB.accounts.find { it.accountId == accountId } ?: throw NotFoundException("Account not found")
            account.balance += amount
            return@map account
        }
    }
}