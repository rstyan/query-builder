package com.tmmwwccc.query_builder.session

import com.tmmwwccc.query_builder.transaction.TransactionFailure
import com.tmmwwccc.query_builder.transaction.TxnManagerFactory
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.transaction.TransactionIsolationLevel

/** Get your dbSession's here! */
class DbSessionFactoryImpl(jdbi: Jdbi) : DbSessionFactory {

    private val txnFactory = TxnManagerFactory(jdbi)

    override suspend fun <T> transaction(
        isolationLevel: TransactionIsolationLevel,
        txn: suspend () -> T
    ): T {
        val txnManager = txnFactory.get()
        try {
            txnManager.begin(isolationLevel)
            val result = txn()
            txnManager.commit()
            return result
        } catch (t: Throwable) {
            txnManager.rollback()
            throw TransactionFailure(t)
        } finally {
            txnManager.close()
        }
    }

    override fun get(): DbSession = DbSession(txnFactory)
}
