package commands


import collection.CommandManager
import collection.IOManager
import model.*
/**
 * Команда вывода справки.
 * Показывает список всех доступных команд и их описание.
 */
class HelpCommand(private val commandManager: CommandManager, private val io: IOManager) : Command {
    override val name = "help"
    override val description = "вывести справку по доступным командам"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        var result = "Доступные команды:\n"

        for (command in commandManager.allCommands()) {
            result += "${command.name}: ${command.description}\n"
        }

        return result
    }
}