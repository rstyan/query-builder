package com.tmmwwccc.query_builder.expression

/**
 * Postgres specific?
 */
class Except(private val a: Statement, private val b: Statement) : Statement() {

    override fun build(): String = "${a.build()} EXCEPT ${b.build()}"
}

infix fun Statement.except(other: Statement) = Except(this, other)
