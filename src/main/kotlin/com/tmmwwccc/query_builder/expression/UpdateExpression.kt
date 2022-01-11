package com.tmmwwccc.query_builder.expression

class UpdateExpression(val name: String, val value: Any): Equal(name, value) {

    constructor(name: Binding, value: Any) :this(name.build(), value)

    override fun build(): String = "$name = $value"
}
infix fun String.setTo(rhs: Any): UpdateExpression = UpdateExpression(this, rhs)
