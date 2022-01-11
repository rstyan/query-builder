package com.tmmwwccc.query_builder.expression

// Assumes "from" and "to" have a toString() method that
// does the *right* thing.
class Range<T>(val from: T, val to: T) : Expression() {

    override fun build(): String = "$from and $to"

    fun toBinding(type: String? = null): Range<Binding> =
        Range(Binding(this.from.toString(), type), Binding(this.to.toString(), type))
}
