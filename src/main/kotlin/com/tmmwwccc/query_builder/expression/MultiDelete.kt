package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** Delete with criteria from mulitple tables */
class MultiDelete(from: DataSet) : Delete(from) {

    private val tables: StringJoiner = StringJoiner(",")

    fun add(table: TableReference): MultiDelete =
        this.apply { tables.add(table.build()) }

    fun where(where: Expression): MultiDelete =
        this.apply { this.where = where }

    override fun build(): String =
        "delete from $from using ${tables}" +  where?.let { " where ${it.build()}"}
}
