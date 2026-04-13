package commands

import exceptions.ExitException
import collection.IOManager

/**
 * Команда выхода из программы.
 * Завершает выполнение без сохранения коллекции.
 */
class ExitCommand(private val io: IOManager) : Command {
    override val name = "exit"
    override val description = "завершить программу"

    override fun execution(args: List<String>) {
        io.println("Программа завершена")
        throw ExitException()
    }
}