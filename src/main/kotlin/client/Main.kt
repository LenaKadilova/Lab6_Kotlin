package client

import common.Request

fun main() {
    System.setOut(java.io.PrintStream(System.out, true, "UTF-8"))
    val client = Client("localhost", 12345)
    val io = IOManager()

    println("Клиент запущен. Введите команду:")

    while (true) {
        io.print("> ")
        io.flush()

        val line = io.readLine()
        if (line.isEmpty()) continue

        val parts = line.trim().split(Regex("\\s+"))
        val commandName = parts[0]
        val argument = parts.drop(1).joinToString(" ").ifEmpty { null }

        if (commandName == "exit") {
            println("Клиент завершён")
            return
        }

        if (commandName == "save") {
            println("Команда save недоступна на клиенте")
            continue
        }

        val dragon = if (commandName in listOf("insert", "update", "replace_if_greater")) {
            io.createDragon(0)
        } else {
            null
        }

        val request = Request(commandName, argument, dragon)
        val response = client.send(request)

        if (response != null) {
            println(response.message)
            response.lines?.forEach { println(it) }
        } else {
            println("Сервер недоступен, попробуйте позже")
        }
    }
}