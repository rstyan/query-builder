package com.tmmwwccc.query_builder.expression

class NotExists(private val query: Statement) : Expression() {

    override fun build(): String = "NOT EXISTS(${query.build()})"
}
