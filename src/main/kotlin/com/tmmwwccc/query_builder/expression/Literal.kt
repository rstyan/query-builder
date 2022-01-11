package com.tmmwwccc.query_builder.expression

/**
 * Represents a literal (quoted) value in an expression
 */
class Literal(private val literal: String) : Expression() {

    override fun build(): String = "'$literal'"
    override fun toString() = build()
}
