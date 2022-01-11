package com.tmmwwccc.query_builder.expression

/** Sometimes you just need a noop expression */
class EmptyExpression(private val value: String = "1") : Expression() {
    override fun build(): String = value
}
