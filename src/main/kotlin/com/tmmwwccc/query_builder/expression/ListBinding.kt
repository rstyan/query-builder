package com.tmmwwccc.query_builder.expression

/**
 * Named list binding parameter, jdbi compatible.
 */

// it seems that, in a conjunction, ListBinding
// needs to come before a regular Binding, else
// jdbi throws a UnableToExecuteStatementException
class ListBinding(name: String) : Binding(name) {

    override fun build(): String = "<$name>"
}
