package commands

import collection.CollectionManager
import model.*
/**
 * Команда замены элемента.
 * Заменяет значение по ключу, если новое значение больше текущего.
 */
class ReplaceIfGreaterCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "replace_if_greater"
    override val description = "заменить значение по ключу, если новое значение больше старого"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        return "Потом"
        //collectionManager.replaceIfGreater()
    }
}