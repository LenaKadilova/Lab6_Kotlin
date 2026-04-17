package commands


import collection.CommandManager
import collection.IOManager
/**
 * Команда вывода справки.
 * Показывает список всех доступных команд и их описание.
 */
class HelpCommand(private val commandManager: CommandManager, private val io: IOManager) : Command {
    override val name = "help"
    override val description = "вывести справку по доступным командам"

    override fun execution(args: List<String>) {
        io.println("Доступные команды:")

        for (command in commandManager.allCommands()) {
            io.println("${command.name}: ${command.description}")
        }
    }
}