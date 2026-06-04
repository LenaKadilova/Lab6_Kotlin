package client

import common.Request
import common.CommandInfo

fun main() {
    System.setOut(java.io.PrintStream(System.out, true, "UTF-8"))
    val client = Client("localhost", 12345)
    val io = IOManager()
    val executingScripts = mutableSetOf<String>()
    val availableCommands = mutableMapOf<String, CommandInfo>()
    val initResponse = client.send(Request("connect", null, null))

    if (initResponse == null) {
        println("Сервер недоступен")
        return
    }

    initResponse.commands.forEach {
        availableCommands[it.name] = it
    }

    println("Клиент запущен. Введите команду:")

    while (true) {
        io.print("> ")
        io.flush()

        val line = io.readLine()
        if (io.currentFile == null) {
            executingScripts.clear()
        }
        if (line.isBlank()) continue

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

        if (commandName == "help") {
            if (availableCommands.isEmpty()) {
                println("Список команд ещё не получен от сервера")
            } else {
                println("Доступные команды:")
                availableCommands.values.forEach { command ->
                    println("${command.name} : ${command.description}")
                }
            }
            continue
        }
        /*if (commandName == "help") {
            println("""Доступные команды:
    help : вывести справку по доступным командам
    info : вывести информацию о коллекции
    show : вывести все элементы коллекции
    insert null {element} : добавить новый элемент с заданным ключом
    update id {element} : обновить значение элемента коллекции, id которого равен заданному
    remove_key null : удалить элемент из коллекции по его ключу
    clear : очистить коллекцию
    execute_script file_name : считать и исполнить скрипт из указанного файла
    exit : завершить программу
    remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
    remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный
    replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
    group_counting_by_id : сгруппировать элементы коллекции по значению поля id
    filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки
    print_ascending : вывести элементы коллекции в порядке возрастания
        """.trimIndent())
            continue
        }*/

        if (commandName == "execute_script") {
            if (argument == null) {
                println("Укажите файл")
                continue
            }
            val fileName = argument

            if (executingScripts.contains(fileName)) {
                println("Обнаружена рекурсия! Скрипт уже выполняется.")
                continue
            }
            val file = java.io.File(fileName)
            if (!file.exists()) {
                println("Файл не найден: $fileName")
                continue
            }
            executingScripts.add(fileName)
            io.setFileInput(file)
            continue
        }

        if (availableCommands.isNotEmpty() && commandName !in availableCommands.keys) {
            println("Команда недоступна на сервере")
            continue
        }

        val dragon = if (commandName in listOf("insert", "update")) {
            io.createDragon(0)
        } else {
            null
        }

        val request = Request(commandName, argument, dragon)
        val response = client.send(request)

        if (response != null) {

            availableCommands.clear()

            response.commands.forEach {
                availableCommands[it.name] = it
            }

            println(response.message)
            response.lines?.forEach { line -> println(line) }

        } else {
            println("Сервер недоступен, попробуйте позже")
        }
    }
}