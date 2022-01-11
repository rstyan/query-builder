package com.tmmwwccc.query_builder.transaction

import org.jdbi.v3.core.transaction.TransactionIsolationLevel

interface TxnManager {

    fun begin(isolationLevel: TransactionIsolationLevel)
    fun commit()
    fun rollback()
    fun close()
}
