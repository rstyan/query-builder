package com.tmmwwccc.query_builder.expression

/*
 * Alias's are needed if a nested query is used in an update 'from' clause
 */
class NestedQuery(val query: Query, val alias: String? = null) : Expression() {

    override fun build(): String {
        val a = alias?.let { " as $it" } ?: ""
        return "(${query.build()}) $a"
    }
}
