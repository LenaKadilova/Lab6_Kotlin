package common.commands

import common.Request
import common.Response
import common.exceptions.ExitException

/**
 * Команда выхода из программы.
 * Завершает выполнение без сохранения коллекции.
 */
class ExitCommand() : Command {
    override val name = "exit"
    override val description = "завершить программу"

    override fun execute(request: Request): Response {
        return Response("exit — только клиентская команда")
    }
}