package commands

import collection.CollectionManager
import collection.IOManager
import model.*
/**
 * Команда очистки коллекции.
 * Удаляет все элементы из коллекции.
 */
class ClearCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "clear"
    override val description = "очистить коллекцию"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        collectionManager.clear()
        return "Коллекция очищена"
    }
}