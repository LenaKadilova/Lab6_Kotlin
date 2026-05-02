package commands

import collection.CollectionManager
import exceptions.ValidationException
import collection.IOManager
import model.*
/**
 * Команда обновления элемента.
 * Обновляет значение элемента по заданному id.
 */
class UpdateCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {
    override val name = "update"
    override val description = "обновить элемент по id"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        if (args.isEmpty()) {
            return "Необходимо указать id"
        }



        val id = try {
            args[0].toInt()
        } catch (e: NumberFormatException) {
            return "id должен быть числом"
        }

        if (dragon == null) {
            return "Ошибка: объект не передан"
        }


        return collectionManager.updateById(id, dragon)
    }

}