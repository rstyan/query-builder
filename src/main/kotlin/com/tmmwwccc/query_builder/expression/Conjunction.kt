package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** join two SQL criteria by AND */
class Conjunction(private val first: Expression) : Expression() {

    companion object {

        // AND all the criteria together!
        fun conjoin(criteria: List<Expression?>): Expression? =
            @Suppress("UnsafeCast") // Get it together Lint, you can cast null to anything.
            criteria.filterNotNull()
                .fold(null as Expression?) { acc, crit ->
                    when (acc) {
                        null -> crit
                        is Conjunction -> acc.and(crit)
                        else -> Conjunction(acc).and(crit)
                    }
                }
    }

    private val predicates: MutableList<Expression> = mutableListOf()

    constructor(criteria: Array<out Expression>) : this(criteria[0]) {
        for (i in 1 until criteria.size) {
            predicates.add(criteria[i])
        }
    }

    infix fun and(clause: Expression): Conjunction =
        this.apply { this.predicates.add(clause) }

    val size: Int
        get() = this.predicates.size + 1

    override fun build(): String = "(${getCriteria()})"

    private fun getCriteria(): String = StringJoiner(" ")
        .add(first.build()).apply {
            for (predicate in predicates) {
                add("and ${predicate.build()}")
            }
        }.toString()
}

infix fun Conjunction.and(other: Expression): Conjunction =
    apply { this.and(other) }
