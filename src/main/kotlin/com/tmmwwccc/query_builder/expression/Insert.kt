package com.tmmwwccc.query_builder.expression

import com.tmmwwccc.query_builder.utils.StringUtils.camelToSnakeCase
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaType

/** Insert a new row */
open class Insert : Statement() {

    private var conflictTarget: ConflictTarget? = null
    private var conflictAction: ConflictAction? = null
    private var sqlTable: TableReference? = null
    private var entity: Any? = null
    private val rows: MutableList<Map<String, Any?>> = mutableListOf()
    private var select: Query? = null

    open fun into(sqlTable: TableReference): Insert =
        this.apply { this.sqlTable = sqlTable }

    fun values(values: Map<String, Any?>): Insert =
        this.apply { this.rows.add(values) }

    fun values(entity: Any): Insert =
        this.apply { this.entity = entity }

    fun from(select: Query): Insert =
        this.apply { this.select = select }

    fun onConflict(action: ConflictAction, target: ConflictTarget? = null): Insert =
        this.apply {
            this.conflictTarget = target
            this.conflictAction = action
        }

    private fun getSqlStatement(): String {
        // Special case for insert from select
        select?.let {
            return "insert into ${sqlTable!!.build()} (${it.columns.joinToString()}) $it"
        }

        // Special case for postgres DEFAULT
        if (entity == null && rows.isEmpty()) {
            return "insert into ${sqlTable!!.build()} values (DEFAULT)"
        }

        val columns = if (rows.isNotEmpty()) {
            // All rows must have the same columns!  So row 0 is as good as any.
            rows[0].keys.joinToString()
        } else {
            properties(entity!!) { s, _ -> s.camelToSnakeCase() }
        }

        val values = if (rows.isNotEmpty()) {
            rows.map { "(${it.values.joinToString()})" }.joinToString()
        } else {
            val results = properties(entity!!) { s, t -> ":$s$t" }
            "($results)"
        }

        return "insert into ${sqlTable!!.build()} ($columns) values $values"
    }

    override fun build(): String =
        "${getSqlStatement()}${
            conflictAction?.let {
                " on conflict${
                    conflictTarget?.let { " $it" } ?: ""
                } do $it"
            } ?: ""
        }"

    override fun toString() = build()

    // Returns a formatted, comma separated list of property names.
    private fun properties(entity: Any, formatter: (name: String, type: String) -> String): String {
        val result = StringJoiner(",")
        for (property in entity::class.memberProperties) {
            result.add(formatter(property.name, property.cast()))
        }
        return result.toString()
    }

    private fun <T, R> KProperty1<T, R>.cast(): String =
        when (this.returnType.javaType.typeName) {
            UUID::class.java.name -> "::UUID"
            else -> ""
        }
}
