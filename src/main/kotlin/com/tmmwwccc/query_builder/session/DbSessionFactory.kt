package com.tmmwwccc.query_builder.session

import org.jdbi.v3.core.transaction.TransactionIsolationLevel

/** Get you query managers here! */
interface DbSessionFactory {

    fun get(): DbSession

    suspend fun <T> transaction(
        isolationLevel: TransactionIsolationLevel = TransactionIsolationLevel.READ_COMMITTED,
        txn: suspend () -> T
    ): T
}
