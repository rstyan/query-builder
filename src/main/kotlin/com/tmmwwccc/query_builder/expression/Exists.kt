package com.tmmwwccc.query_builder.expression

class Exists(private val query: Statement) : Expression() {

    override fun build(): String = "EXISTS(${query.build()})"
}
