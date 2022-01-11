package com.tmmwwccc.query_builder.expression

class Like private constructor (
    val arg: Expression,
    val column: Column,
    val negate: Boolean = false,
    val noCase: Boolean = true
) : AbstractCompare<Like>() {

    constructor(
        column: Column,
        arg: String,
        negate: Boolean = false,
        noCase: Boolean = true
    ) : this(Literal(arg), column, negate, noCase)

    constructor(
        column: Column,
        arg: Binding,
        negate: Boolean = false,
        noCase: Boolean = true
    ) : this(arg, column, negate, noCase)

    override fun build(): String =
        "$column " + (if (negate) "NOT " else "") + "${if (noCase) "" else "i"}LIKE $arg"
}
infix fun Column.ilike(arg: String): Like = Like(this, arg, noCase=false)
infix fun Column.ilike(arg: Binding): Like = Like(this, arg, noCase=false)
infix fun Column.like(arg: String): Like = Like(this, arg)
infix fun Column.like(arg: Binding): Like = Like(this, arg)
infix fun Column.notLike(arg: String): Like = Like(this, arg, negate=true)
