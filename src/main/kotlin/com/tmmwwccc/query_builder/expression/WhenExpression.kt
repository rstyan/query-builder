package com.tmmwwccc.query_builder.expression

class WhenExpression(
    private val test: Expression,
    private val trueValue: Any,
    private val falseValue: Any
) : Expression() {

    override fun build(): String =
        "CASE WHEN ${test.build()} THEN $trueValue ELSE $falseValue END"
}
