package client

import network.Request
import network.Response
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import model.*

class SimpleClient(
    private val host: String,
    private val port: Int
) {

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
        val type = DragonType.valueOf(readln())

        println("Введите характер (WISE, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE):")
        val character = DragonCharacter.valueOf(readln())

        println("Введите количество глаз:")
        val eyes = readln().toInt()

        println("Введите количество зубов:")
        val teeth = readln().toDouble()

        return Dragon(
            id = 0, // сервер потом может заменить
            name = name,
            coordinates = Coordinates(x, y),
            creationDate = java.time.LocalDateTime.now(),
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = DragonHead(eyes, teeth)
        )
    }

    fun start() {
        println("Клиент запущен")
        println("Введите команду")

        while (true) {
            print("> ")

            val line = readlnOrNull()?.trim() ?: break

            if (line.isBlank()) continue

            val parts = line.split(" ")
            val commandName = parts[0]
            val args = parts.drop(1)

            var dragon: Dragon? = null

            if (commandName == "insert" || commandName == "update" || commandName == "replace_if_greater" || commandName == "remove_greater") {
                dragon = readDragon()
            }

            val request = Request(
                commandName = commandName,
                args = args,
                dragon = dragon
            )


            try {
                val socket = Socket(host, port)

                val output = ObjectOutputStream(socket.getOutputStream())
                val input = ObjectInputStream(socket.getInputStream())

                output.writeObject(request)
                output.flush()

                val response = input.readObject() as Response

                println(response.message)

                input.close()
                output.close()
                socket.close()

                if (commandName == "exit") {
                    println("Клиент завершён")
                    break
                }

            } catch (e: Exception) {
                println("Сервер временно недоступен: ${e.message}")
            }
        }
    }
}