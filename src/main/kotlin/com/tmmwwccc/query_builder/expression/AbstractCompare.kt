package com.tmmwwccc.query_builder.expression

/** SQL compare expression */
abstract class AbstractCompare<T> : Expression() {

    protected var lhs: String = "1"
    protected var op: String = "="
    protected var rhs: String = "?"

    fun using(op: String): AbstractCompare<T> =
        this.apply { this.op = op }

    fun lhs(lhs: String): AbstractCompare<T> =
        this.apply { this.lhs = lhs }

    fun lhs(operand: Column): AbstractCompare<T> =
        this.apply { this.lhs = operand.build() }

    private fun add(rhs: String): AbstractCompare<T> =
        this.apply { this.rhs = rhs }

    fun rhs(operand: Any): AbstractCompare<T> =
        this.apply {
            if (operand is Expression) this.add(operand.build())
            else this.add(operand.toString())
        }

    infix fun and(rhs: Expression): Conjunction =
        Conjunction(this).and(rhs)

    infix fun or(rhs: Expression): Disjunction =
        Disjunction(this).or(rhs)

    override fun build(): String = "($lhs)$op($rhs)"
}
