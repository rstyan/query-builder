package com.tmmwwccc.query_builder.expression

open class GreaterThan() : AbstractCompare<GreaterThan>() {

    constructor(lhs: String, rhs: Any) : this() {
        lhs(lhs)
        using(">")
        rhs(rhs)
    }

    constructor(lhs: Column, rhs: Any) : this() {
        lhs(lhs)
        using(">")
        rhs(rhs)
    }
}

infix fun String.gt(rhs: Any): GreaterThan = GreaterThan(this, rhs)
infix fun Column.gt(rhs: Any): GreaterThan = GreaterThan(this, rhs)
