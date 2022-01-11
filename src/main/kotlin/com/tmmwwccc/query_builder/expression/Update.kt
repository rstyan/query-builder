package com.tmmwwccc.query_builder.expression

import org.joda.time.DateTime
import java.util.StringJoiner
import java.util.UUID

class Update(private val resource: TableReference) : Statement() {

    private val assignments: MutableList<UpdateExpression> = mutableListOf()
    private var where: Expression = EmptyExpression()
    private var from: List<Expression> = listOf()

    fun set(value: UpdateExpression): Update {
        this.assignments.add(value)
        return this
    }

    fun set(column: String, value: Any): Update =
        apply {
            val theValue = when (value) {
                is String -> "'$value'"
                is DateTime -> "'$value'"
                is UUID -> "'$value'::UUID"
                else -> value
            }
            this.assignments.add(UpdateExpression(column, theValue))
        }

    fun from(from: List<TableReference>): Update =
        this.apply { this.from = from }

    fun from(from: TableReference): Update =
        this.apply { this.from = listOf(from) }

    fun from(from: NestedQuery): Update =
        this.apply { this.from = listOf(from) }

    fun set(column: Column, value: Any): Update =
        apply { set(column.build(), value) }

    /**
     * Returns a conjunction of the input criteria
     * For disjunctions use where(new Disjunction(....).or(...))
     */
    fun where(vararg criteria: Expression): Update {
        if (criteria.isEmpty()) {
            this.where = EmptyExpression()
        } else if (criteria.size == 1) {
            this.where = criteria[0]
        } else {
            val conjunction = Conjunction(criteria[0])
            for (i in 1 until criteria.size) {
                conjunction.and(criteria[i])
            }
            this.where = conjunction
        }
        return this
    }

    override fun build(): String {
        val sets = StringJoiner(",")
        for (set in this.assignments) {
            sets.add(set.build())
        }
        return "update $resource set $sets" +
                if (from.isNotEmpty()) " from ${from.joinToString(",")}" else {""} +
                " where ${where.build()}"
    }

    override fun toString() = build()

    val isEmpty: Boolean
        get() = assignments.size == 0
}

