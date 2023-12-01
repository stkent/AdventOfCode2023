@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package utils

enum class GridDirection {
    N, E, S, W;

    fun toVector(length: Int = 1): GridVector2d {
        return when (this) {
            //@formatter:off
            N -> GridVector2d( 0,  length)
            E -> GridVector2d( length,  0)
            S -> GridVector2d( 0, -length)
            W -> GridVector2d(-length,  0)
            //@formatter:on
        }
    }

    fun left90() = entries[(ordinal + 3) % 4]

    fun right90() = entries[(ordinal + 1) % 4]

    @Suppress("FunctionName")
    fun `180`() = entries[(ordinal + 2) % 4]
}
