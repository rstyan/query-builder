package com.tmmwwccc.query_builder.expression

open class GreaterOrEqual() : AbstractCompare<GreaterOrEqual>() {

    constructor(lhs: String, rhs: Any) : this() {
        lhs(lhs)
        using(">=")
        rhs(rhs)
    }

    constructor(lhs: Column, rhs: Any) : this() {
        lhs(lhs)
        using(">=")
        rhs(rhs)
    }
}

infix fun String.gte(rhs: Any): GreaterOrEqual = GreaterOrEqual(this, rhs)
infix fun Column.gte(rhs: Any): GreaterOrEqual = GreaterOrEqual(this, rhs)
