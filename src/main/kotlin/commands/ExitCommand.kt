package commands

import exceptions.ExitException
import collection.IOManager
import model.*

/**
 * Команда выхода из программы.
 * Завершает выполнение без сохранения коллекции.
 */
class ExitCommand(private val io: IOManager) : Command {
    override val name = "exit"
    override val description = "завершить программу"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        return "Ghjuhfvvf pfdthityf"
    }
}