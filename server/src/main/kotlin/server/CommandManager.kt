package server

import server.commands.Command
import common.Request
import common.Response
import common.CommandInfo

/**
 * Класс для управления командами.
 * Хранит список доступных команд и выполняет их по имени.
 */
class CommandManager {
    private val commands: MutableMap <String, Command> = linkedMapOf()
    /**
     * Добавляет команду в список.
     * @param command команда
     */
    fun addToList (command: Command) {
        commands[command.name] = command
    }
    /**
     * Выполняет команду по запросу.
     *
     * @param request запрос от клиента
     * @return результат выполнения команды
     */
    fun execute(request: Request): Response {
        val command = commands[request.commandName]
            ?: return Response("Команда не найдена")

        return command.execute(request)
    }
    /**
     * Возвращает список всех команд.
     * @return список команд
     */
    fun allCommands(): List<Command>     = commands.values.toList()


    fun getCommandInfo(): List<CommandInfo> {
        return commands.values.map { command ->
            CommandInfo(
                name = command.name,
                description = command.description,
                needArgument = command.name in listOf(
                    "insert",
                    "update",
                    "remove_key",
                    "remove_greater_key",
                    "filter_starts_with_name",
                    "execute_script"
                ),
                needDragon = command.name in listOf(
                    "insert",
                    "update",
                    "remove_greater"
                )
            )
        }
    }


    fun getCommandDescriptions(): Map<String, String> {
        return commands.mapValues { it.value.description }
    }
}