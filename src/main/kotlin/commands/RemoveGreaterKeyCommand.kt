package commands

import collection.CollectionManager
import collection.IOManager
/**
 * Команда удаления по ключу.
 * Удаляет элементы, ключ которых больше заданного.
 */
class RemoveGreaterKeyCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "remove_greater_key"
    override val description = "удалить из коллекции все элементы, ключ которых больше заданного"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            io.println("Необходимо указать ключ")
            return
        }

        val key: Long

        try {
            key = args[0].toLong()
        } catch (e: NumberFormatException) {
            io.println("Ключ должен быть числом")
            return
        }

        collectionManager.removeGreaterKey(key)
    }
}