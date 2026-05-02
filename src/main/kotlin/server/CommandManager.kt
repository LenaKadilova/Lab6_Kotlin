package server

import common.commands.Command
import common.Request
import common.Response

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
}