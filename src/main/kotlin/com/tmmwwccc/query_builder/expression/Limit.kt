package com.tmmwwccc.query_builder.expression

/**
 * Limit the number of rows returned, starting at the specified offset.
 */
class Limit(val limit: Int, val offset: Int? = null) : Expression() {

    companion object {
        const val ALL = -1
    }

    override fun build(): String {
        val actualLimit = if (limit == ALL) "ALL" else limit.toString()
        return "limit $actualLimit" + offset?.let { " offset $it" }
    }
}

