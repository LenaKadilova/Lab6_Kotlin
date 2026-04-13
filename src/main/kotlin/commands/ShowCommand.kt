package commands


import collection.CollectionManager
/**
 * Команда вывода элементов коллекции.
 * Отображает все элементы в строковом представлении.
 */
class ShowCommand(private val collectionManager: CollectionManager) : Command {
    override val name = "show"
    override val description = "вывести все элементы коллекции"

    override fun execution(args: List<String>) {
        collectionManager.ShowAll()
    }
}