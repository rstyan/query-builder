package com.tmmwwccc.query_builder.expression

open class LessOrEqual() : AbstractCompare<LessOrEqual>() {

    constructor(lhs: String, rhs: Any) : this() {
        lhs(lhs)
        using("<=")
        rhs(rhs)
    }

    constructor(lhs: Column, rhs: Any) : this() {
        lhs(lhs)
        using("<=")
        rhs(rhs)
    }
}

infix fun String.lte(rhs: Any): LessOrEqual = LessOrEqual(this, rhs)
infix fun Column.lte(rhs: Any): LessOrEqual = LessOrEqual(this, rhs)
