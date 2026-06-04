package server

import server.commands.*
import java.time.LocalDateTime

fun main(args: Array<String>) {
    System.setOut(java.io.PrintStream(System.out, true, "UTF-8"))
    if (args.isEmpty()) {
        println("Ошибка: нужно передать имя файла")
        return
    }

    val fileName = args[0]
    val fileManager = FileManager(fileName)
    val collectionManager = CollectionManager(LocalDateTime.now(), fileName)
    val commandManager = CommandManager()

    try {
        collectionManager.loadCollectionFromFile(fileManager)
        println("Коллекция загружена: ${collectionManager.size()} элементов")
    } catch (e: Exception) {
        println("Не удалось загрузить коллекцию: ${e.message}")
    }

    commandManager.addToList(HelpCommand(commandManager))
    commandManager.addToList(InfoCommand(collectionManager))
    commandManager.addToList(ShowCommand(collectionManager))
    commandManager.addToList(ClearCommand(collectionManager))
    commandManager.addToList(PrintAscendingCommand(collectionManager))
    commandManager.addToList(InsertCommand(collectionManager))
    commandManager.addToList(UpdateCommand(collectionManager))
    commandManager.addToList(RemoveGreaterKeyCommand(collectionManager))
    commandManager.addToList(FilterStartsWithNameCommand(collectionManager))
    commandManager.addToList(GroupCountingByIdCommand(collectionManager))
    commandManager.addToList(RemoveGreaterCommand(collectionManager))
    commandManager.addToList(RemoveKeyCommand(collectionManager))
    commandManager.addToList(ReplaceIfGreaterCommand(collectionManager))

    val server = Server(collectionManager, commandManager)
    server.start()
}