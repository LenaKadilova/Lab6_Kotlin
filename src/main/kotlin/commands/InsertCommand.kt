package commands

import collection.CollectionManager
import exceptions.ValidationException
import collection.IOManager
import model.*

/**
 * Команда добавления элемента.
 * Добавляет новый элемент с заданным ключом в коллекцию.
 */
class InsertCommand(private val collectionManager: CollectionManager, private val io: IOManager): Command {
    override val name = "insert"
    override val description = "добавить новый элемент с заданным ключом"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        if (args.isEmpty()) {
            return "Необходимо указать ключ"
        }

        val key: Long
        try {
            key = args[0].toLong()
        }
        catch (e: NumberFormatException){
            return "Ключ должен быть числом"
        }

        if (dragon == null) {
            return "Ошибка: объект не передан"
        }

        if (collectionManager.storage.containsKey(key)) {
            return "Уже существует элемент с таким ключом"
        }

        //try {
        //    val dragon = collectionManager.createDragon(collectionManager.nextId())
        //        collectionManager.storage[key] = dragon
        //    io.println("Дракон добавлен")
        //}
        //catch (e: ValidationException) {
        //    io.println("Ошибка: ${e.message}")
        //}
        collectionManager.storage[key] = dragon
        return "Дракончика добавили"
    }
}