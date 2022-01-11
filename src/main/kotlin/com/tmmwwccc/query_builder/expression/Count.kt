package com.tmmwwccc.query_builder.expression

class Count(column: Column) : Column(column) {

    constructor(column: String) : this(Column(column))

    private var criteria: Expression? = null

    fun filter(criteria: Expression) : Count =
        this.apply { this.criteria = criteria }

    override fun build(): String {
        return "count(${super.qualifiedName()})" +
                criteria?.let { " filter (where $it)" } +
                super.aliasSuffix()
    }
}
