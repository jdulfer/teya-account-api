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
        // check if there is an appropriate amount of money in the senders account
        val senderAccount = accountDAO.getAccount(senderAccountId)
        val receiverAccount = accountDAO.getAccount(receiverAccountId)
        if (senderAccount.balance < amount) {
            throw Exception("Insufficient funds in sender account")
        }

        // create the transaction
        // TODO :: Might need to specifically handle transactional error
        val createdTransactions = transactionDAO.moveMoney(senderAccountId, receiverAccountId, amount)

        // change the balances of the sender and receiver accounts
        try {
            val senderTransaction = createdTransactions.find { it.accountId == senderAccountId }
                ?: throw Exception("Transaction failed")
            val receiverTransaction = createdTransactions.find { it.accountId == receiverAccountId }
                ?: throw Exception("Transaction failed")
            changeAccountBalance(senderAccount, senderTransaction)
            changeAccountBalance(receiverAccount, receiverTransaction)
        } catch (e: Exception) {
            transactionDAO.reverseTransactions(createdTransactions)
            throw e
        }
    }

    private fun changeAccountBalance(
        account: Account,
        transaction: Transaction,
    ) {
        when (transaction.transactionType) {
            Transaction.TransactionType.INCOMING -> {
                account.balance += transaction.amount
            }

            Transaction.TransactionType.OUTGOING -> {
                account.balance -= transaction.amount
            }

            else -> {
                throw Exception("Invalid transaction type")
            }
        }
    }

}