package commands

import collection.CollectionManager
import collection.IOManager
import model.*

/**
 * Команда вывода информации о коллекции.
 * Отображает тип коллекции, дату инициализации и количество элементов.
 */
class InfoCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {
    override val name = "info"
    override val description = "вывести информацию о коллекции (тип, дата инициализации, количество элементов, файл)"

    override fun execution(args: List<String>, dragon: Dragon?):  String {
        return "Тип коллекции: java.util.Hashtable\n" +
                "Дата инициализации: ${collectionManager.time}\n" +
                "Количество элементов: ${collectionManager.size()}"
    }
}