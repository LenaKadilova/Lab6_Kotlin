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
    fun execution (name: String, args: List<String>): Boolean {
        val command = commands[name]
        if (command == null) {
            return false
        }
        command.execution(args)
        return true
    }
    /**
     * Возвращает список всех команд.
     * @return список команд
     */
    fun allCommands(): List<Command>     = commands.values.toList()
}