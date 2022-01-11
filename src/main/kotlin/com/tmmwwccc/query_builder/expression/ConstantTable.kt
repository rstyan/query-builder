package com.tmmwwccc.query_builder.expression

/** Ad Hoc Table constructed with VALUES */
class ConstantTable(
    val name: String
) : TableReference(null, name) {
    private val rows: MutableList<Map<String, Any?>> = mutableListOf()
    private val rowValues: MutableList<List<Any?>> = mutableListOf()

    fun values(values: Map<String, Any?>): ConstantTable =
        this.apply { this.rows.add(values) }

    fun values(values: List<Any?>): ConstantTable =
        this.apply { this.rowValues.add(values) }

    override fun build(): String {

        val columnValues: String =
            if (rows.isNotEmpty())
                rows.joinToString { "(${it.values.joinToString()})" }
            else
                rowValues.joinToString {
                    "(${
                        it.map { it?.let { "('${it.escapeQuotes()}')" } ?: "null" }.joinToString()
                    })"
                }

        val columnNames: String =
            if (rows.isNotEmpty()) "(${rows[0].keys.joinToString()})" else ""

        return "(VALUES$columnValues)$name$columnNames"
    }

    override fun toString() = build()

    private fun Any.escapeQuotes() = this.toString().replace("'", "''")
}
