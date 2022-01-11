package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** Basic SQL Select */
@Suppress("StringLiteralDuplication", "TooManyFunctions")
open class Query private constructor(
    private val select: ResultSet,
    private val isDistinct: Boolean = false
) : Statement() {

    private var from: DataSet? = null
    private var where: Expression? = null
    private var groupBy: GroupBy? = null
    private var orderBy: OrderBy? = null
    private var limit: Limit? = null

    companion object {

        fun select(selection: ResultSet, isDistinct: Boolean = false): Query =
            Query(selection, isDistinct)

        fun select(vararg columns: String, isDistinct: Boolean = false): Query {
            val resultSet = ResultSet()
            for (column in columns) resultSet.add(column)
            return Query(resultSet, isDistinct)
        }

        fun select(selection: String, table: TableReference, isDistinct: Boolean = false): Query =
            Query(ResultSet().add(Column(selection, table)), isDistinct)

        fun select(vararg columns: Column, isDistinct: Boolean = false): Query {
            val resultSet = ResultSet()
            for (column in columns) resultSet.add(column)
            return Query(resultSet, isDistinct)
        }
    }

    fun from(from: DataSet): Query =
        this.apply { this.from = from }

    /**
     * Returns a conjunction of the input criteria
     * For disjunctions use where(new Disjunction(....).or(...))
     */
    open fun where(vararg criteria: Expression): Query =
        this.apply {
            if (criteria.isNotEmpty()) {
                where = Conjunction(criteria)
            }
        }

    fun orderBy(column: String, direction: OrderBy.Direction = OrderBy.Direction.ASC): Query =
        this.apply { this.orderBy = OrderBy(column, direction) }

    fun orderBy(column: Column, direction: OrderBy.Direction = OrderBy.Direction.ASC): Query =
        this.apply { this.orderBy = OrderBy(column, direction) }

    fun orderBy(orderBy: OrderBy): Query =
        this.apply { this.orderBy = orderBy }

    fun groupBy(group: GroupBy): Query =
        this.apply { this.groupBy = group }

    fun groupBy(group: String): Query =
        this.apply { this.groupBy = GroupBy(group) }

    fun groupBy(group: Column): Query =
        this.apply { this.groupBy = GroupBy(group) }

    fun limit(limit: Int, offset: Int = 0): Query =
        this.apply { this.limit = Limit(limit, offset) }

    fun limit(limit: Limit): Query =
        this.apply { this.limit = limit }

    override fun build(): String {
        val distinct = if (isDistinct) "distinct" else ""
        return StringJoiner(" ").add("select $distinct ${select.build()}").apply {
            from?.let { add("from ${it.build()}") }
            where?.let { add("where ${it.build()}") }
            groupBy?.let { add(it.build()) }
            orderBy?.let { add(it.build()) }
            limit?.let { add(it.build()) }
        }.toString()
    }

    override fun toString() = build()

    val columns: List<String> = select.columnNames
}
