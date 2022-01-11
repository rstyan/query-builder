package com.tmmwwccc.query_builder.expression

/** Reference to an SQL table! */
@Suppress("TooManyFunctions")
// just as many as necessary, Your Majesty
open class TableReference(
    val schema: String?,
    val table: String,
    val alias: String? = null
) : DataSet() {

    fun copy(alias: String): TableReference =
        TableReference(this.schema, this.table, alias)

    fun col(columnName: String): Column =
        Column(columnName, this)

    fun alias(): String = alias?.let { alias } ?: table

    fun name(): String = (schema?.let { "$schema." } ?: "") + table

    override fun build(): String =
        name() + (alias?.let { " as $it" } ?: "")

    override fun toString() = build()
}
