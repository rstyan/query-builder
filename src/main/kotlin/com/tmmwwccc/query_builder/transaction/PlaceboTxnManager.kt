package com.tmmwwccc.query_builder.transaction

import org.jdbi.v3.core.transaction.TransactionIsolationLevel

/**
 * Provided to handle nested transactions.  i.e. if there is already
 * a transaction in effect this manager is used.  It won't do silly
 * things like close the outer transaction when the inner one
 * completes.
 */
internal class PlaceboTxnManager : TxnManager {

    override fun begin(isolationLevel: TransactionIsolationLevel) = Unit

    override fun commit() = Unit

    override fun rollback() = Unit

    override fun close() = Unit
}
