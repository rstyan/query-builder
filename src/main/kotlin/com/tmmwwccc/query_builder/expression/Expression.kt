package com.tmmwwccc.query_builder.expression

/** Generic SQL expression */
abstract class Expression : Builder<String> {

    override fun toString(): String = this.build()
}
