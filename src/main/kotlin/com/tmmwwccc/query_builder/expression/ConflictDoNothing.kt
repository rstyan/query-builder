package com.tmmwwccc.query_builder.expression

class ConflictDoNothing : ConflictAction() {

    override fun build(): String = "nothing"
}
