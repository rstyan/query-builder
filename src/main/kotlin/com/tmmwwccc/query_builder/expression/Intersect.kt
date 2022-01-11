package com.tmmwwccc.query_builder.expression

/**
 * Postgres specific?
 */
class Intersect(private val a: Statement, private val b: Statement) : Statement() {

    override fun build(): String = "${a.build()} INTERSECT ${b.build()}"
}

infix fun Statement.intersect(other: Statement) = Intersect(this, other)
