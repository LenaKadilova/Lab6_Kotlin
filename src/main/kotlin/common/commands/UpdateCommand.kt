package commands

import collection.CollectionManager
import exceptions.ValidationException
import collection.IOManager
import common.Request
import common.Response
/**
 * Команда обновления элемента.
 * Обновляет значение элемента по заданному id.
 */
class UpdateCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {
    override val name = "update"
    override val description = "обновить элемент по id"

    override fun execute(request: Request): Response {
        val args = request.argument?.split(" ") ?: emptyList()

        if (args.isEmpty()) {
            return Response("Необходимо указать id")
        }

        val id = try {
            args[0].toLong()
        } catch (e: NumberFormatException) {
            return Response("id должен быть числом")
        }

        return try {
            val dragon = collectionManager.createDragon(id.toInt())
            collectionManager.updateById(id, dragon)
            Response("Элемент обновлён")
        } catch (e: ValidationException) {
            Response("Ошибка: ${e.message}")
        }
    }
}