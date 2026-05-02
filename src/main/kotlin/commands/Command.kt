package commands

import model.*
/**
 * Интерфейс для команд.
 */
interface Command {
    val name: String
    val description: String

    fun execution(args: List <String>, dragon: Dragon?) : String
}