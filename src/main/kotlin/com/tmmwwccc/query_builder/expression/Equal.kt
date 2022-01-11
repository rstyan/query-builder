package com.tmmwwccc.query_builder.expression

/** Compare column and value for equality */
open class Equal() : AbstractCompare<Equal>() {

    constructor(lhs: String, rhs: Any) : this() {
        lhs(lhs)
        using("=")
        rhs(rhs)
    }

    constructor(lhs: Column, rhs: Any) : this() {
        lhs(lhs)
        using("=")
        rhs(rhs)
    }
}

infix fun String.eq(rhs: Any): Equal = Equal(this, rhs)
infix fun Column.eq(rhs: Any): Equal = Equal(this, rhs)
