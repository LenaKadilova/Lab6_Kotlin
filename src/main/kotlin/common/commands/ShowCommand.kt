package commands


import collection.CollectionManager
import common.Request
import common.Response
/**
 * Команда вывода элементов коллекции.
 * Отображает все элементы в строковом представлении.
 */
class ShowCommand(private val collectionManager: CollectionManager) : Command {
    override val name = "show"
    override val description = "вывести все элементы коллекции"

    override fun execute(request: Request): Response {
        val data = collectionManager.ShowAll()
        return Response("Коллекция:", data)
    }
}