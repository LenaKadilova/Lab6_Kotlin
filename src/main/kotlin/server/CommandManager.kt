package collection

import commands.Command

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
     * Выполняет команду по имени.
     *
     * @param name имя команды
     * @param args аргументы команды
     * @return true если команда найдена и выполнена, иначе false
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