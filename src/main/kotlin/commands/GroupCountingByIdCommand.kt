package commands

import collection.CollectionManager
import model.*
/**
 * Команда группировки элементов.
 * Группирует элементы по id и выводит количество в каждой группе.
 */
class GroupCountingByIdCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "group_counting_by_id"
    override val description = "сгруппировать элементы по id"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        if (collectionManager.storage.isEmpty()) {
            return "Коллекция пустая"
        }

        var result = ""


        for (dragon in collectionManager.storage.values) {
            result += "id = ${dragon.id}: 1\n"
        }
        return result
    }
}