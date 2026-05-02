package common.commands

import server.CollectionManager
import common.Request
import common.Response
/**
 * Команда очистки коллекции.
 * Удаляет все элементы из коллекции.
 */
class ClearCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "clear"
    override val description = "очистить коллекцию"

    override fun execute(request: Request): Response {
        collectionManager.clear()
        return Response("Коллекция очищена")
    }
}