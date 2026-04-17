package commands

import collection.CollectionManager
/**
 * Команда удаления элементов.
 * Удаляет все элементы, превышающие заданный.
 */
class RemoveGreaterCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "remove_greater"
    override val description = "удалить из коллекции элементы больше заданного"

    override fun execution(args: List<String>) {

        collectionManager.removeGreater()

    }
}