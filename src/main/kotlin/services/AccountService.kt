package com.teya.services

import com.teya.daos.AccountDAO
import com.teya.daos.TransactionDAO
import com.teya.domain.Account
import com.teya.domain.Transaction
import java.math.BigDecimal

class AccountService(private val accountDAO: AccountDAO, private val transactionDAO: TransactionDAO) {

    fun getAccountBalance(accountId: String): BigDecimal {
        val account = accountDAO.getAccount(accountId)
        return account.balance.setScale(2)
    }

    suspend fun getAccountHistory(accountId: String): List<Transaction> = transactionDAO.getTransactions(accountId)

    suspend fun moveMoney(senderAccountId: String, receiverAccountId: String, amount: BigDecimal) {
        val senderAccount = accountDAO.getAccount(senderAccountId)
        val receiverAccount = accountDAO.getAccount(receiverAccountId)
        if (senderAccount.balance < amount) {
            throw Exception("Insufficient funds in sender account")
        }

        val createdTransactions = transactionDAO.moveMoney(senderAccountId, receiverAccountId, amount)

        try {
            val senderTransaction = createdTransactions.find { it.accountId == senderAccountId }
                ?: throw Exception("Transaction failed")
            val receiverTransaction = createdTransactions.find { it.accountId == receiverAccountId }
                ?: throw Exception("Transaction failed")

            changeAccountBalances(
                listOf(
                    senderAccount to senderTransaction,
                    receiverAccount to receiverTransaction
                )
            )

        } catch (e: Exception) {
            transactionDAO.reverseTransactions(createdTransactions)
            throw e
        }
    }

    private fun changeAccountBalances(balanceUpdates: List<Pair<Account, Transaction>>) {
        balanceUpdates.map { (account, transaction) ->
            when (transaction.transactionType) {
                Transaction.TransactionType.INCOMING -> {
                    account.accountId to transaction.amount
                }

                Transaction.TransactionType.OUTGOING -> {
                    account.accountId to transaction.amount.negate()
                }

                else -> {
                    throw Exception("Invalid transaction type")
                }
            }
        }.let { accountDAO.changeAccountBalances(it) }
    }

}