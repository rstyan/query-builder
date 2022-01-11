package com.tmmwwccc.query_builder.expression

open class NotEqual() : AbstractCompare<NotEqual>() {

    constructor(lhs: String, rhs: Any) : this() {
        lhs(lhs)
        using("!=")
        rhs(rhs)
    }

    constructor(lhs: Column, rhs: Any) : this() {
        lhs(lhs)
        using("!=")
        rhs(rhs)
    }
}

infix fun String.neq(rhs: Any): NotEqual = NotEqual(this, rhs)
infix fun Column.neq(rhs: Any): NotEqual = NotEqual(this, rhs)
