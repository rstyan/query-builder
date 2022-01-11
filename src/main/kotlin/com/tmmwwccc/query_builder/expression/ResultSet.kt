package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/**
 * Specify the columns of a query result.  If a resource is specified then
 * the columns will be prefixed with its alias so you can specify multiple
 * result sets.
 *
 * default: *
 */
class ResultSet(initialSet: ResultSet? = null) : Expression() {

    private val columns: MutableList<Column> = mutableListOf()
    private var isDistinct = false;

    init {
        initialSet?.let { columns.addAll(it.columns) }
    }

    fun add(column: String): ResultSet =
        this.apply { this.columns.add(Column(column)) }

    fun add(selection: String, table: TableReference): ResultSet =
        this.apply { this.columns.add(Column(selection, table)) }

    fun add(column: Column): ResultSet =
        this.apply { this.columns.add(column) }

    fun add(columns: List<Column>): ResultSet =
        this.apply { columns.forEach { this.columns.add(it) }}

    fun distinct(): ResultSet =
        this.apply { this.isDistinct = true }

    val columnNames: List<String> get() = columns.map { it.columnName }

    override fun build(): String {
        val resultSet = StringJoiner(",")
        for (column in columns) {
            resultSet.add(column.build())
        }
        return (if (isDistinct) "distinct " else "") + resultSet.toString()
    }
}
