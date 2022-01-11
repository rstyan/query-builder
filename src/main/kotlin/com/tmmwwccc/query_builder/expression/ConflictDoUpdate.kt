package com.tmmwwccc.query_builder.expression

class ConflictDoUpdate(
    private val columnName: String,
    private val columnValue: Expression
) : ConflictAction() {

    override fun build(): String ="update set $columnName = $columnValue"
}
