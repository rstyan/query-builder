package com.tmmwwccc.query_builder.transaction

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi

/**
 * Transaction Manager Factory.  The goal is to be able to handle
 * nested transactions - i.e. on method, bundled up as a transaction
 * is called by another method, also declared as a transaction.  The
 * nested method should not be able to commit or rollback; only the outer
 * one should be able to do that.
 */
class TxnManagerFactory(private val jdbi: Jdbi) {

    // Non-null means we're in a transaction.
    private var txnHandle: Handle? = null

    fun get(): TxnManager {
        if (!inTransaction) {
            txnHandle = jdbi.open()
            return StandardTxnManager(txnHandle!!)
        }
        return PlaceboTxnManager()
    }

    fun useHandle(f: (h: Handle) -> Unit) {
        if (inTransaction) {
            f(txnHandle!!)
        } else {
            jdbi.useHandle<TransactionFailure> { f(it) }
        }
    }

    fun <T> withHandle(f: (h: Handle) -> T): T =
        if (inTransaction) {
            f(txnHandle!!)
        } else {
            jdbi.withHandle<T, TransactionFailure> { f(it) }
        }

    private val inTransaction: Boolean
        get() = !(txnHandle?.isClosed ?: true)
}
