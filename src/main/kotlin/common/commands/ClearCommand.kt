package commands

import collection.CollectionManager
import collection.IOManager
import common.Request
import common.Response
/**
 * Команда очистки коллекции.
 * Удаляет все элементы из коллекции.
 */
class ClearCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "clear"
    override val description = "очистить коллекцию"

    override fun execution(request: Request): Response {
        collectionManager.clear()
        return Response("Коллекция очищена")
    }
}