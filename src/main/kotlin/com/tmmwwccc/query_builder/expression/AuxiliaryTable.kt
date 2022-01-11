package com.tmmwwccc.query_builder.expression

/** Reference to a postgres common table */
class AuxiliaryTable(val name: String) : TableReference(schema = null, table = name)
