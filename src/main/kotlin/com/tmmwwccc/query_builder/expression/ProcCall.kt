package com.tmmwwccc.query_builder.expression

/**
 * Stored procedure call in a select list.
 */
class ProcCall(private val name: String, arg: Column) : Column(arg) {

    constructor(name: String, arg: String) : this(name, Column(arg))

    constructor(name: String, arg: Expression) : this(name, Column(arg.build()))

    constructor(name: String, vararg args: Any)
            : this(name, args.joinToString(",") { it.toArg() })

    override fun qualifiedName(): String = "$name(${super.qualifiedName()})"
}

private fun Any.toArg() = when(this) {
    is String -> "'$this'"
    else -> this.toString()
}
