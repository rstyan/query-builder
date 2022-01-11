package com.tmmwwccc.query_builder.expression

/**
 * Sometimes you need nothing at all.
 */
class NullElement : Expression() {

    override fun build(): String = ""
}
