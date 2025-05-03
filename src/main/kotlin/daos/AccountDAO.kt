package com.teya.daos

import com.teya.domain.Account

interface AccountDAO {
    fun getAccount(accountId: String): Account
}