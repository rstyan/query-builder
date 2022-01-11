package com.tmmwwccc.query_builder.expression

class ConflictTarget(
    private val constraintName: String? = null
) : Expression() {

    override fun build(): String = constraintName?.let { "on constraint $it" }?: ""
}
