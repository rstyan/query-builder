package com.tmmwwccc.query_builder.expression

class AuxiliaryQuery(
    val table: AuxiliaryTable,
    val query: Statement
) : Expression() {

    override fun build(): String =
        "${table.build()} as (${query.build()})"
}
