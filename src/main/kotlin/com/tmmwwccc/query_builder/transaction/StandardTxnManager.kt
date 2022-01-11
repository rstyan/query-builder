package com.tmmwwccc.query_builder.transaction

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.transaction.TransactionIsolationLevel

internal class StandardTxnManager(
    private val txnHandle: Handle
) : TxnManager {

    override fun begin(isolationLevel: TransactionIsolationLevel) {
        txnHandle.begin()
        if (isolationLevel != TransactionIsolationLevel.READ_COMMITTED) {
            txnHandle.setTransactionIsolation(isolationLevel)
        }
    }

    override fun commit() {
        txnHandle.commit()
    }

    override fun rollback() {
        txnHandle.rollback()
    }

    override fun close() {
        txnHandle.close()
    }
}

