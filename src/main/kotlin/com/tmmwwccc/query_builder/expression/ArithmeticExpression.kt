package com.tmmwwccc.query_builder.expression

sealed class ArithmeticExpression(
    private val column: Column,
    private val op: String,
    private val value: Int
) : Expression() {

    constructor(column: String, op: String, value: Int) :
            this(Column(column), op, value)

    override fun build(): String =
        "${column.build()} $op $value"
}

class Plus(column: String, value: Int) : ArithmeticExpression(column, "+", value)
class Minus(column: String, value: Int) : ArithmeticExpression(column, "-", value)
