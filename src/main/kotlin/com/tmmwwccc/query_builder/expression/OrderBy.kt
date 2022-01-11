package com.tmmwwccc.query_builder.expression

import java.util.StringJoiner

/** SQL order by expression */
class OrderBy() : Expression() {

    private val columns: StringJoiner = StringJoiner(",")

    /** sort direction */
    enum class Direction(val direction: String) {
        ASC("asc"), DESC("desc")
    }

    enum class NullPrecedence {
        FIRST, LAST
    }

    constructor(column: String) : this() {
        this.add(column)
    }

    constructor(column: String, dir: Direction) : this() {
        this.add(column, dir)
    }

    constructor(column: String, dir: Direction, nullPrecedence: NullPrecedence) : this() {
        this.add(Column(column), dir, nullPrecedence)
    }

    constructor(column: Column, dir: Direction, nullPrecedence: NullPrecedence) : this() {
        this.add(column, dir, nullPrecedence)
    }

    constructor(column: Column) : this() {
        this.add(column)
    }

    constructor(column: Column, dir: Direction) : this() {
        this.add(column, dir)
    }

    constructor(order: OrderBy) : this() {
        this.add(order.columns.toString())
    }

    fun add(selector: String): OrderBy =
        this.apply { this.add(Column(selector), Direction.ASC) }

    fun add(column: String, dir: Direction): OrderBy =
        this.apply { this.add(Column(column), dir) }

    fun add(column: Column): OrderBy =
        this.apply { this.add(column, Direction.ASC) }

    fun add(column: Column, direction: Direction): OrderBy =
        this.apply { this.columns.add("${column.build()} ${direction.name}") }

    fun add(column: Column, direction: Direction, nullPrecedence: NullPrecedence): OrderBy =
        this.apply {
            this.columns.add("${column.build()} ${direction.name} NULLS ${nullPrecedence.name}")
        }

    override fun build(): String = "order by $columns"
}
