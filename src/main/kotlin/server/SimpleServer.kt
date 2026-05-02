package server

import collection.CollectionManager
import collection.CommandManager
import collection.FileManager
import collection.IOManager
import commands.ShowCommand
import commands.InfoCommand
import commands.ClearCommand
import commands.ExecuteScriptCommand
import commands.RemoveKeyCommand
import commands.HelpCommand
import commands.ExitCommand
import commands.FilterStartsWithNameCommand
import commands.GroupCountingByIdCommand
import commands.InsertCommand
import commands.PrintAscendingCommand
import commands.RemoveGreaterCommand
import commands.RemoveGreaterKeyCommand
import commands.ReplaceIfGreaterCommand
import commands.SaveCommand
import commands.UpdateCommand
import network.Request
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.time.LocalDateTime

class SimpleServer(
    private val port: Int
) {

    fun start() {
        val serverSocket = ServerSocket(port)
        println("Сервер запущен на порту $port")

        // === Инициализация менеджеров ===
        val io = IOManager()
        val fileName = "data.json"
        val fileManager = FileManager(fileName)

        val collectionManager = CollectionManager(
            LocalDateTime.now(),
            fileName,
            io
        )

        try {
            collectionManager.loadCollectionFromFile(fileManager)
            io.println("Коллекция загружена. Количество элементов: ${collectionManager.Size()}")
        } catch (e: Exception) {
            io.eprintln("Ошибка загрузки: ${e.message}")
        }

        // === Команды ===
        val commandManager = CommandManager()

        commandManager.addToList(HelpCommand(commandManager, io))
        commandManager.addToList(InfoCommand(collectionManager, io))
        commandManager.addToList(ShowCommand(collectionManager))
        commandManager.addToList(ExitCommand(io))
        commandManager.addToList(ClearCommand(collectionManager, io))
        commandManager.addToList(PrintAscendingCommand(collectionManager))
        commandManager.addToList(SaveCommand(collectionManager, io))
        commandManager.addToList(InsertCommand(collectionManager, io))
        commandManager.addToList(UpdateCommand(collectionManager, io))
        commandManager.addToList(RemoveGreaterKeyCommand(collectionManager, io))
        commandManager.addToList(FilterStartsWithNameCommand(collectionManager, io))
        commandManager.addToList(GroupCountingByIdCommand(collectionManager))
        commandManager.addToList(RemoveGreaterCommand(collectionManager))
        commandManager.addToList(RemoveKeyCommand(collectionManager, io))
        commandManager.addToList(ReplaceIfGreaterCommand(collectionManager))
        commandManager.addToList(ExecuteScriptCommand(collectionManager, io))

        val handler = RequestHandler(commandManager)


        while (true) {
            println("Ожидание клиента")

            val clientSocket = serverSocket.accept()
            println("Клиент подключился")

            val input = ObjectInputStream(clientSocket.getInputStream())
            val output = ObjectOutputStream(clientSocket.getOutputStream())

            try {
                val request = input.readObject() as Request
                println("Получен запрос: $request")

                val response = handler.handle(request)

                output.writeObject(response)
                output.flush()

            } catch (e: Exception) {
                println("Ошибка обработки: ${e.message}")
            }

            input.close()
            output.close()
            clientSocket.close()
        }
    }
}