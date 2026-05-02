package commands

import collection.CollectionManager
import model.*
/**
 * Команда сортировки элементов.
 * Выводит элементы коллекции в порядке возрастания.
 */
class PrintAscendingCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "print_ascending"
    override val description = "вывести элементы в порядке возрастания"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        val param = args.firstOrNull()
        return collectionManager.printAscending(param)
    }
}