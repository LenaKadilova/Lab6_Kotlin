package commands

import collection.CollectionManager
import collection.IOManager

/**
 * Команда вывода информации о коллекции.
 * Отображает тип коллекции, дату инициализации и количество элементов.
 */
class InfoCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {
    override val name = "info"
    override val description = "вывести информацию о коллекции (тип, дата инициализации, количество элементов, файл)"

    override fun execution(args: List<String>) {
        io.println("Тип коллекции: java.util.Hashtable")
        io.println("Дата инициализации: ${collectionManager.time}")
        io.println("Количество элементов: ${collectionManager.size()}")
        //println("Файл: ${collectionManager.FileName}")
    }
}