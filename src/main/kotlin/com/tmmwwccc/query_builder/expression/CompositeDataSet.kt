package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/**
 *  A *composite* is the data set that results from an SQL join
 *  operation.
 */
class CompositeDataSet(
    private val base: DataSet,
    private val joinTo: DataSet,
    private val joinBy: JoinExpression
) : DataSet() {

    override fun build(): String = "(${getCriteria()})"

    private fun getCriteria(): String = StringJoiner(" ")
        .add(base.build())
        .add(joinBy.toString())
        .add(joinTo.build())
        .apply {
            joinBy.criteria?.let { add("on ${it.build()}") }
                ?: joinBy.usingColumn?.let { add("using ($it)") }
        }
        .toString()
}
