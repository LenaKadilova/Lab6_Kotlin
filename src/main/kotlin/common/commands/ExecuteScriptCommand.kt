package common.commands

import server.CollectionManager
import server.CommandManager
import client.IOManager
import common.Request
import common.Response
/**
 * Команда выполнения скрипта.
 * Считывает команды из файла и выполняет их последовательно.
 */
class ExecuteScriptCommand(
    private val collectionManager: CollectionManager,
    private val io: IOManager
) : Command {

    override val name = "execute_script"
    override val description = "выполнить скрипт из файла"

    override fun execute(request: Request): Response {
        val args = request.argument?.split(" ") ?: emptyList()

        if (args.isEmpty()) {
            return Response("Укажите файл")
        }

        val fileName = args[0]
        collectionManager.executeScript(fileName)

        return Response("Скрипт выполнен")
    }
}