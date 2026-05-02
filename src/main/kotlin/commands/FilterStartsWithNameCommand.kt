package commands

import collection.CollectionManager
import collection.IOManager
import model.*
/**
 * Команда фильтрации элементов.
 * Выводит элементы, имя которых начинается с заданной строки.
 */
class FilterStartsWithNameCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "filter_starts_with_name"
    override val description = "вывести элементы, имя которых начинается с подстроки"

    override fun execution(args: List<String>, dragon: Dragon?): String {

        if (args.isEmpty()) {
            return "Введите строку"
        }

        val prefix = args[0]
        return collectionManager.filterStartsWithName(prefix)
    }
}