package com.tmmwwccc.query_builder.expression

/** Test if column has a null value */
class IsNull(private val fieldName: Column) : Expression() {

    constructor(fieldName: String) : this(Column(fieldName))

    override fun build(): String = "$fieldName is NULL"
}

