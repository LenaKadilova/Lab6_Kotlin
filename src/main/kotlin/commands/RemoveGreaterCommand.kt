package commands

import collection.CollectionManager
import model.*
/**
 * Команда удаления элементов.
 * Удаляет все элементы, превышающие заданный.
 */
class RemoveGreaterCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "remove_greater"
    override val description = "удалить из коллекции элементы больше заданного"

    override fun execution(args: List<String>, dragon: Dragon?): String {

        //collectionManager.removeGreater()
        val param = args.getOrNull(0)
        val value = args.getOrNull(1)

        return collectionManager.removeGreater(param, value)
    }
}