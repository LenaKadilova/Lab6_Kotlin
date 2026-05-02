package client

import model.*
import network.Request
import network.Response
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.time.LocalDateTime

class SimpleClient(
    private val host: String,
    private val port: Int
) {

    fun start() {
        println("Клиент запущен")

        while (true) {
            print("> ")
            val line = readlnOrNull()?.trim() ?: break

            if (line.isBlank()) continue

            val parts = line.split(" ")
            val commandName = parts[0]
            val args = parts.drop(1)

            if (commandName == "exit") {
                println("Клиент завершён")
                break
            }

            if (commandName == "execute_script") {
                if (args.isEmpty()) {
                    println("Укажите файл")
                } else {
                    executeScript(args[0])
                }
                continue
            }

            processCommand(line)
        }
    }

    private fun processCommand(line: String, scriptDragon: Dragon? = null) {
        val parts = line.trim().split(" ")
        val commandName = parts[0]
        val args = parts.drop(1)

        var dragon: Dragon? = scriptDragon

        if (dragon == null &&
            (commandName == "insert" ||
                    commandName == "update" ||
                    commandName == "replace_if_greater")
        ) {
            dragon = readDragon()
        }

        try {
            val socket = Socket(host, port)

            val output = ObjectOutputStream(socket.getOutputStream())
            val input = ObjectInputStream(socket.getInputStream())

            val request = Request(commandName, args, dragon)

            output.writeObject(request)
            output.flush()

            val response = input.readObject() as Response
            println(response.message)

            input.close()
            output.close()
            socket.close()

        } catch (e: Exception) {
            println("Ошибка: ${e.message}")
        }
    }

    private fun executeScript(fileName: String) {
        try {
            val file = File(fileName)

            if (!file.exists()) {
                println("Файл не найден")
                return
            }

            val lines = file.readLines().filter { it.isNotBlank() }
            var i = 0

            while (i < lines.size) {
                val line = lines[i].trim()
                println("> $line")

                val parts = line.split(" ")
                val commandName = parts[0]

                if (commandName == "insert" ||
                    commandName == "update" ||
                    commandName == "replace_if_greater"
                ) {
                    val (dragon, newIndex) = readDragonFromScript(lines, i + 1)
                    processCommand(line, dragon)
                    i = newIndex
                } else {
                    processCommand(line)
                    i++
                }
            }

        } catch (e: Exception) {
            println("Ошибка выполнения скрипта: ${e.message}")
        }
    }


    private fun readDragonFromScript(lines: List<String>, index: Int): Pair<Dragon, Int> {
        val name = lines[index]
        val x = lines[index + 1].toFloat()
        val y = lines[index + 2].toLong()
        val age = lines[index + 3].toLong()
        val weight = lines[index + 4].toDouble()
        val type = DragonType.valueOf(lines[index + 5].uppercase())
        val character = DragonCharacter.valueOf(lines[index + 6].uppercase())

        val createHead = lines[index + 7].lowercase()

        val head: DragonHead?
        var newIndex: Int

        if (createHead == "yes") {
            val eyes = lines[index + 8].toInt()
            val teeth = lines[index + 9].toDouble()
            head = DragonHead(eyes, teeth)
            newIndex = index + 10
        } else {
            head = null
            newIndex = index + 8
        }

        val dragon = Dragon(
            id = 0,
            name = name,
            coordinates = Coordinates(x, y),
            creationDate = LocalDateTime.now(),
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = head
        )

        return Pair(dragon, newIndex)
    }


    private fun readDragon(): Dragon {
        println("Введите имя:")
        val name = readln()

        println("Введите x:")
        val x = readln().toFloat()

        println("Введите y:")
        val y = readln().toLong()

        println("Введите age:")
        val age = readln().toLong()

        println("Введите weight:")
        val weight = readln().toDouble()

        println("Введите тип (WATER, UNDERGROUND, AIR, FIRE):")
        val type = DragonType.valueOf(readln().uppercase())

        println("Введите характер (WISE, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE):")
        val character = DragonCharacter.valueOf(readln().uppercase())

        println("Создать голову? (yes/no):")
        val createHead = readln().lowercase()

        val head = if (createHead == "yes" || createHead == "y") {
            println("Введите количество глаз:")
            val eyes = readln().toInt()

            println("Введите количество зубов:")
            val teeth = readln().toDouble()

            DragonHead(eyes, teeth)
        } else {
            null
        }

        return Dragon(
            id = 0,
            name = name,
            coordinates = Coordinates(x, y),
            creationDate = LocalDateTime.now(),
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = head
        )
    }
}