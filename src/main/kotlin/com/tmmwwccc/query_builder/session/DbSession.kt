package com.tmmwwccc.query_builder.session

import com.tmmwwccc.query_builder.expression.*
import com.tmmwwccc.query_builder.transaction.TxnManagerFactory
import org.jdbi.v3.core.Handle

/**
 * Execute various queries.
 *
 * The bindings combine two concepts supported by jdbi:
 * 1) Single valued objects can be bound directly to a named key
 * 2) A list of single valued objects can be bound to a a named key
 *
 */
@Suppress("TooManyFunctions")
open class DbSession constructor(
    val txnFactory: TxnManagerFactory
) {


    inline fun <reified T> findOne(q: Statement, bindings: Map<String, Any> = mapOf()): T? =
        txnFactory.withHandle { h ->
            h.getQuery(q, bindings).mapTo(T::class.java).list().let {
                if (it.isEmpty()) null else it[0]
            }
        }

    inline fun <reified T> find(q: Statement, bindings: Map<String, Any> = mapOf()): List<T> =
        txnFactory.withHandle {
            it.getQuery(q, bindings).mapTo(T::class.java).list()
        }

    fun Handle.getQuery(
        q: Statement,
        bindings: Map<String, Any>
    ): org.jdbi.v3.core.statement.Query =
        this.createQuery(q.build()).apply {

            val objBindings = bindings.filterNot { it.value is List<*> }
            if (objBindings.isNotEmpty()) {
                this.bindMap(objBindings)
            }
            val listBindings = bindings.filter { it.value is List<*> }
            if (listBindings.isNotEmpty()) {
                listBindings.entries.forEach { mapItem ->
                    @Suppress("UnsafeCast") // Its not unsafe, Lint, try harder.
                    val list: List<String> = (mapItem.value as List<*>).map { it.toString() }
                    this.bindList(mapItem.key, list)
                }
            }
        }

    fun saveAndGetId(q: Statement, values: Any): Long {
        var update: Long = 0
        txnFactory.useHandle { h ->
            @Suppress("UnsafeCast") // Maybe if you read the docs, Lint.
            update = h.createUpdate(q.build()).bindBean(values)
                .executeAndReturnGeneratedKeys().mapToMap()
                .findOnly().get("id") as Long
        }
        return update
    }

    fun saveAndGetId(q: Statement, bindings: Map<String, Any?>): Long {
        var update: Long = 0
        txnFactory.useHandle { h ->
            @Suppress("UnsafeCast") // Maybe if you read the docs, Lint.
            update = h.createUpdate(q.build()).bindMap(bindings)
                .executeAndReturnGeneratedKeys().mapToMap()
                .findOnly().get("id") as Long
        }
        return update
    }

    fun saveAndGetId(q: Statement): Long {
        var update: Long = 0
        txnFactory.useHandle { h ->
            @Suppress("UnsafeCast") // Maybe if you read the docs, Lint.
            update = h.createUpdate(q.build())
                .executeAndReturnGeneratedKeys().mapToMap()
                .findOnly().get("id") as Long
        }
        return update
    }

    fun save(q: Statement, values: Any) =
        txnFactory.useHandle { h ->
            h.createUpdate(q.build()).bindBean(values).execute()
        }

    fun save(q: Statement, bindings: Map<String, Any?>) =
        txnFactory.useHandle { h ->
            h.createUpdate(q.build()).bindMap(bindings).execute()
        }

    fun save(q: Statement) =
        txnFactory.useHandle { h ->
            h.createUpdate(q.build()).execute()
        }

    fun delete(q: Delete, bindings: Map<String, Any>? = null) {
        txnFactory.useHandle { h ->
            h.createUpdate(q.build()).bindMap(bindings).execute()
        }
    }

    fun insert(table: TableReference, values: Any) {
        val query = Insert().into(table).values(values)
        save(query, values)
    }

    fun insertOrIgnore(table: TableReference, values: Any) {
        val query = Insert()
            .into(table)
            .values(values)
            .onConflict(ConflictDoNothing())
        save(query, values)
    }

    fun insertAndGetId(table: TableReference, values: Any): Long {
        val query = Insert().into(table).values(values)
        return saveAndGetId(query, values)
    }
}
