package com.tmmwwccc.query_builder.expression

class Between<T>(
    private val operand: Column,
    private val range: Range<T>
): Expression() {
    constructor(operand: String, range: Range<T>) : this(Column(operand), range)
    constructor(operand: Binding, range: Range<T>) : this(Column(operand), range)

    override fun build(): String = "${operand.build()} between ${range.build()}"
}

infix fun <T> String.between(rhs: Range<T>) = Between(this, rhs)
infix fun <T> Column.between(rhs: Range<T>) = Between(this, rhs)
infix fun <T> Binding.between(rhs: Range<T>) = Between(this, rhs)
