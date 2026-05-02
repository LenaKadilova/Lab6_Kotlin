package common.commands

import server.CollectionManager
import common.Request
import common.Response
/**
 * Команда сортировки элементов.
 * Выводит элементы коллекции в порядке возрастания.
 */
class PrintAscendingCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "print_ascending"
    override val description = "вывести элементы в порядке возрастания"

    override fun execute(request: Request): Response {
        val param = request.argument ?: return Response("Укажите параметр сортировки")
        return Response("Отсортированная коллекция:", lines = collectionManager.printAscending(param))
    }
}