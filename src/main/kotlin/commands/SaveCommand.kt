package commands

import collection.CollectionManager
import collection.IOManager
import model.*
/**
 * Команда сохранения коллекции.
 * Сохраняет текущее состояние коллекции в файл.
 */
class SaveCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "save"
    override val description = "сохранить коллекцию в файл"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        if (args.isEmpty()){
            return "Ошибка: нужно указать имя файла"
        }
        val fileName = args[0]
        collectionManager.save(fileName)
        return "Коллекция сохранена в $fileName"
    }
}