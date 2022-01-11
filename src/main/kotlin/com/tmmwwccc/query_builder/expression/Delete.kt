package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** SQL Delete operator */
open class Delete constructor(
    protected val from: DataSet
) : Statement() {

    protected var where: Expression? = null
    protected var using: List<TableReference> = listOf()

    /**
     * Returns a conjunction of the input criteria
     * For disjunctions use where(new Disjunction(....).or(...))
     */
    fun where(vararg criteria: Expression): Delete =
        this.apply {
            if (criteria.isNotEmpty()) {
                where = Conjunction(criteria)
            }
        }

    fun using(joins: List<TableReference>): Delete =
        this.apply { this.using = joins }

    fun using(table: TableReference): Delete =
        this.apply { this.using = listOf(table) }

    override fun build(): String =
        StringJoiner(" ").add("delete from ${from.build()}").apply {
            if (using.isNotEmpty()) {
                add("using ${using.joinToString(",")}")
            }
            where?.let {
                add("where ${it.build()}")
            }
        }.toString()
}
