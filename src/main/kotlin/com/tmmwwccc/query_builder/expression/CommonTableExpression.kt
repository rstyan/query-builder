package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/**
 * Postgres's extension for writing auxiliary statements
 * for use in larger expressions.
 */
class CommonTableExpression(
    private val auxiliaryQueries: List<AuxiliaryQuery>,
    private val mainQuery: Statement
) : Statement() {

    constructor(table: AuxiliaryTable, auxiliaryQuery: Statement, mainQuery: Statement)
            : this(listOf(AuxiliaryQuery(table, auxiliaryQuery)), mainQuery)

    override fun build(): String {
        val auxQueries = StringJoiner(",")
        for (aux in auxiliaryQueries) {
            auxQueries.add(aux.build())
        }
        return "with $auxQueries ${mainQuery.build()}"
    }
}
