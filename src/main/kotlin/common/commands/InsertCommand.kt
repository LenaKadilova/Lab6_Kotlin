package commands

import collection.CollectionManager
import exceptions.ValidationException
import collection.IOManager
import common.Request
import common.Response
/**
 * Команда добавления элемента.
 * Добавляет новый элемент с заданным ключом в коллекцию.
 */
class InsertCommand(private val collectionManager: CollectionManager, private val io: IOManager): Command {
    override val name = "insert"
    override val description = "добавить новый элемент с заданным ключом"

    override fun execute(request: Request): Response {
        val args = request.argument?.split(" ") ?: emptyList()

        if (args.isEmpty()) {
            return Response("Необходимо указать ключ")
        }

        val key: Long = try {
            args[0].toLong()
        } catch (e: NumberFormatException) {
            return Response("Ключ должен быть числом")
        }

        if (collectionManager.storage.containsKey(key)) {
            return Response("Уже существует элемент с таким ключом")
        }

        return try {
            val dragon = collectionManager.createDragon(collectionManager.nextId())
            collectionManager.storage[key] = dragon
            Response("Дракон добавлен")
        } catch (e: ValidationException) {
            Response("Ошибка: ${e.message}")
        }
    }
}