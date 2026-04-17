package commands

import collection.CollectionManager
import collection.IOManager
/**
 * Команда фильтрации элементов.
 * Выводит элементы, имя которых начинается с заданной строки.
 */
class FilterStartsWithNameCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "filter_starts_with_name"
    override val description = "вывести элементы, имя которых начинается с подстроки"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            io.println("Введите строку")
            return
        }

        val prefix = args[0]
        collectionManager.filterStartsWithName(prefix)
    }
}