package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** join two SQL criteria by OR */
class Disjunction(private val first: Expression) : Expression() {

    private val predicates: MutableList<Expression> = mutableListOf()

    infix fun or(clause: Expression): Disjunction =
        this.apply { this.predicates.add(clause) }

    fun size(): Int = this.predicates.size + 1

    override fun build(): String = "(${getCriteria()})"

    private fun getCriteria(): String =
        StringJoiner(" ").add(first.build()).apply {
            for (predicate in predicates) {
                add("OR ${predicate.build()}")
            }
        }.toString()
}

infix fun Disjunction.or(other: Expression): Disjunction =
    apply { this.or(other) }
