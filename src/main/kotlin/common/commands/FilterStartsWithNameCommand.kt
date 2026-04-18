package commands

import collection.CollectionManager
import collection.IOManager
import common.Request
import common.Response
/**
 * Команда фильтрации элементов.
 * Выводит элементы, имя которых начинается с заданной строки.
 */
class FilterStartsWithNameCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "filter_starts_with_name"
    override val description = "вывести элементы, имя которых начинается с подстроки"

    override fun execute(request: Request): Response {

        val args = request.argument?.split(" ") ?: emptyList()

        if (args.isEmpty()) {
            return Response("Введите строку")
        }

        val prefix = args[0]
        val result = collectionManager.filterStartsWithName(prefix)

        return Response("Результат:", result)
    }
}