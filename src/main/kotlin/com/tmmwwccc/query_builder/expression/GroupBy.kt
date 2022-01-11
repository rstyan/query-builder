package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** Group by expression */
class GroupBy(column: String) : Expression() {

    private val columns: StringJoiner = StringJoiner(",")

    init {
        columns.add(column)
    }

    constructor(column: Column) : this(column.toString())

    fun and(column: Column): GroupBy =
        this.apply { this.columns.add(column.build()) }

    fun and(column: String): GroupBy =
        this.apply { this.columns.add(column) }

    fun and(columns: List<Column>): GroupBy =
        this.apply { columns.forEach { and(it) }}

    override fun build(): String = "group by $columns"
}
