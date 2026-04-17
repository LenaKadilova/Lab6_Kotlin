package commands

import collection.CollectionManager
import collection.IOManager
/**
 * Команда сохранения коллекции.
 * Сохраняет текущее состояние коллекции в файл.
 */
class SaveCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "save"
    override val description = "сохранить коллекцию в файл"

    override fun execution(args: List<String>) {
        if (args.isEmpty()){
            io.println("Ошибка: нужно указать имя файла")
            return
        }
        val fileName = args[0]
        collectionManager.save(fileName)
    }
}