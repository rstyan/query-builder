package com.tmmwwccc.query_builder.expression

/** Join Expression */
open class JoinExpression(
    private val dataSet: DataSet,
    private val toDataSet: DataSet,
    private val joinType: DataSet.Type? = null
) {
    var criteria: Expression? = null
    var usingColumn: String? = null

    infix fun on(criteria: Expression): CompositeDataSet {
        this.criteria = criteria
        return CompositeDataSet(dataSet, toDataSet, this)
    }

    infix fun using(columnName: String): CompositeDataSet {
        this.usingColumn = columnName
        return CompositeDataSet(dataSet, toDataSet, this)
    }

    override fun toString(): String = joinType?.type ?: DataSet.Type.INNER.type
}
