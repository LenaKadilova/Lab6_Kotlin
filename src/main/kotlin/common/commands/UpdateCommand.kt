package commands

import collection.CollectionManager
import exceptions.ValidationException
import collection.IOManager
/**
 * Команда обновления элемента.
 * Обновляет значение элемента по заданному id.
 */
class UpdateCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {
    override val name = "update"
    override val description = "обновить элемент по id"

    override fun execution(args: List<String>) {
        if (args.isEmpty()) {
            io.println("Необходимо указать id")
            return
        }

        val id: Long
        try {
            id = args[0].toLong()
        } catch (e: NumberFormatException) {
            io.println("id должен быть числом")
            return
        }

        try {
            val dragon = collectionManager.createDragon(id.toInt())
            collectionManager.updateById(id, dragon)
        }
        catch (e: ValidationException) {
            io.println("Ошибка: ${e.message}")
        }
    }
}