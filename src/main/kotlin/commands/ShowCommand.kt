package commands


import collection.CollectionManager
import model.*
/**
 * Команда вывода элементов коллекции.
 * Отображает все элементы в строковом представлении.
 */
class ShowCommand(private val collectionManager: CollectionManager) : Command {
    override val name = "show"
    override val description = "вывести все элементы коллекции"

    override fun execution(args: List<String>, dragon: Dragon?): String {
        return collectionManager.showAsString()
    }
}