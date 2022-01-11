package com.tmmwwccc.query_builder.expression

/**
 * Represents a column specification in an SQL result set.
 * */
open class Column(
    private val name: String,
    private val table: TableReference? = null,
    private val type: String? = null
) : Expression() {

    private var alias: String? = null

    constructor(query: Query) :
            this("(${query.build()})", null)

    constructor(expr: Expression) :
            this(expr.build(), null)

    override fun build(): String =
        "${qualifiedName()}${aliasSuffix()}"

    infix fun `as`(alias: String): Column =
        this.apply { this.alias = alias }

    private fun tablePrefix(): String =
        table?.let { "${it.alias()}." } ?: ""

    private fun typeCast(): String =
        type?.let { "::$it" } ?: ""

    protected fun aliasSuffix(): String =
        alias?.let { " as $it" } ?: ""

    protected open fun qualifiedName(): String =
        "${tablePrefix()}$name${typeCast()}"

    override fun toString(): String = qualifiedName()

    val columnName: String get() = alias ?: name
}
