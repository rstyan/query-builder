package com.tmmwwccc.query_builder.expression

/**
 * element is one that can be used in join expressions
 * such as a join (b join c on b.id=c.id) on ...
 */
abstract class DataSet : Expression() {

    /** join type */
    enum class Type(val type: String) {
        INNER("inner join"),
        OUTER("left outer join"),
        RIGHT_INNER("right inner join"),
        RIGHT_OUTER("right outer join"),
        FULL_OUTER("full outer join")
    }

    infix fun join(other: DataSet): JoinExpression =
        JoinExpression(this, other)

    fun join(other: DataSet, joinType: Type): JoinExpression =
        JoinExpression(this, other, joinType)

    infix fun leftOuterJoin(other: DataSet): JoinExpression =
        join(other, Type.OUTER)

    infix fun rightOuterJoin(other: DataSet): JoinExpression =
        join(other, Type.RIGHT_OUTER)

    infix fun fullOuterJoin(other: DataSet): JoinExpression =
        join(other, Type.FULL_OUTER)
}
