package com.tmmwwccc.query_builder.expression

/**
 * Postgres specific?
 */
class Union(private val a: Statement, private val b: Statement) : Statement() {

    override fun build(): String = "${a.build()} UNION ${b.build()}"
}

infix fun Statement.union(other: Statement) = Union(this, other)
