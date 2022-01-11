package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** Test if column equals any of the specified values */
class InCollection private constructor(
    private val collection: Collection<*>? = null,
    private val binding: ListBinding? = null
) : AbstractCompare<Equal>() {


    init {
        this.using("in")
    }

    constructor(lhs: String, rhs: Collection<*>) : this(collection = rhs) {
        lhs(lhs)
    }

    constructor(lhs: Column, rhs: Collection<*>) : this(collection = rhs) {
        lhs(lhs)
    }

    constructor(lhs: Column, binding: ListBinding) : this(binding = binding) {
        lhs(lhs)
    }

    constructor(lhs: String, binding: ListBinding) : this(binding = binding) {
        lhs(lhs)
    }

    override fun build(): String =
        String.format("($lhs $op (%s))", collection?.let { getValues(it) } ?: binding!!.build())

    private fun getValues(collection: Collection<*>): String {
        val sb = StringJoiner(",")
        for (value in collection) {
            when (value) {
                is String -> sb.add("'$value'")
                else -> sb.add(value.toString())
            }
        }
        return sb.toString()
    }
}

infix fun <T> String.`in`(rhs: Collection<T>) = InCollection(this, rhs)
infix fun <T> Column.`in`(rhs: Collection<T>) = InCollection(this, rhs)
infix fun String.`in`(rhs: ListBinding) = InCollection(this, rhs)
infix fun Column.`in`(rhs: ListBinding) = InCollection(this, rhs)
