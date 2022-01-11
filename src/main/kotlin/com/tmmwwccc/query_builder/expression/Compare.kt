package com.tmmwwccc.query_builder.expression

/** compare two SQL columns */
class Compare() : AbstractCompare<Compare>() {

    constructor(lhs: String, op: String, rhs: String) : this() {
        lhs(lhs)
        using(op)
        rhs(rhs)
    }

    constructor(lhs: Column, op: String, rhs: String) : this() {
        lhs(lhs)
        using(op)
        rhs(rhs)
    }
}

infix fun String.lt(rhs: String): Compare = Compare(this, "<", rhs)
infix fun String.lt(rhs: Binding): Compare = Compare(this, "<", rhs.build())
infix fun String.lte(rhs: String): Compare = Compare(this, "<=", rhs)
infix fun String.lte(rhs: Binding): Compare = Compare(this, "<=", rhs.build())
infix fun String.gt(rhs: String): Compare = Compare(this, ">", rhs)
infix fun String.gt(rhs: Binding): Compare = Compare(this, ">", rhs.build())
infix fun String.gte(rhs: String): Compare = Compare(this, ">=", rhs)
infix fun String.gte(rhs: Binding): Compare = Compare(this, ">=", rhs.build())
infix fun Column.neq(rhs: String): Compare = Compare(this, "!=", rhs)
infix fun Column.neq(rhs: Binding): Compare = Compare(this, "!=", rhs.build())
infix fun Column.neq(rhs: Literal): Compare = Compare(this, "!=", rhs.build())
