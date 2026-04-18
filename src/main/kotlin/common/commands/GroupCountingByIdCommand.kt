package commands

import collection.CollectionManager
import common.Request
import common.Response
/**
 * Команда группировки элементов.
 * Группирует элементы по id и выводит количество в каждой группе.
 */
class GroupCountingByIdCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "group_counting_by_id"
    override val description = "сгруппировать элементы по id"

    override fun execute(request: Request): Response {
        val result = collectionManager.groupCountingById()
        return Response("Группировка:", result)
    }
}