package commands

import collection.CollectionManager
import collection.IOManager
/**
 * Команда удаления элемента по ключу.
 * Удаляет элемент коллекции по заданному ключу.
 */
class RemoveKeyCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "remove_key"
    override val description = "удалить элемент по ключу"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            io.println("Введите ключ")
            return
        }


        val key = try {
            args[0].toLong()
        }
        catch (e: NumberFormatException) {
            io.println("Ключ должен быть числом")
            return
        }

        collectionManager.removeByKey(key)
    }
}