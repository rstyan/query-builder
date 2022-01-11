package com.tmmwwccc.query_builder.expression

/**
 * Named binding parameter, jdbi compatible, with optional type for cast.
 */
open class Binding(val name: String, val type: String?=null) : Expression() {

    override fun build(): String = ":$name" + (type?.let { "::$it" } ?: "")
    override fun toString() = build()
}
