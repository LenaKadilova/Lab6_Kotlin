package commands

import collection.CollectionManager
import collection.IOManager
import model.*
/**
 * Команда удаления элемента по ключу.
 * Удаляет элемент коллекции по заданному ключу.
 */
class RemoveKeyCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "remove_key"
    override val description = "удалить элемент по ключу"

    override fun execution(args: List<String>, dragon: Dragon?): String {

        if (args.isEmpty()) {
            return "Введите ключ"
        }


        val key = try {
            args[0].toLong()
        }
        catch (e: NumberFormatException) {
            return "Ключ должен быть числом"
        }

        val existed = collectionManager.storage.containsKey(key)
        collectionManager.removeByKey(key)


        return if (existed) {
            "Элемент удалён"
        } else {
            "Ключ не найден"
        }
    }
}