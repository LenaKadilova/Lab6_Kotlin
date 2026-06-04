package common

import java.io.Serializable

data class CommandInfo(
    val name: String,
    val description: String,
    val needArgument: Boolean,
    val needDragon: Boolean
) : Serializable