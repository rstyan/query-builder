package com.tmmwwccc.query_builder.expression

open class LessThan() : AbstractCompare<LessThan>() {

    constructor(lhs: String, rhs: Any) : this() {
        lhs(lhs)
        using("<")
        rhs(rhs)
    }

    constructor(lhs: Column, rhs: Any) : this() {
        lhs(lhs)
        using("<")
        rhs(rhs)
    }
}

infix fun String.lt(rhs: Any): LessThan = LessThan(this, rhs)
infix fun Column.lt(rhs: Any): LessThan = LessThan(this, rhs)
