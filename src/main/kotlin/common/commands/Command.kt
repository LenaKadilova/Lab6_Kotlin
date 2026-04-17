package commands
/**
 * Интерфейс для команд.
 */
interface Command {
    val name: String
    val description: String

    fun execute(request: Request): Response
}