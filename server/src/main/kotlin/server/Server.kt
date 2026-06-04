package server

import common.Request
import common.Response
import org.slf4j.LoggerFactory
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

class Server(
    collectionManager: CollectionManager,
    private val commandManager: CommandManager
) {

    private val logger = LoggerFactory.getLogger(Server::class.java)

    fun start() {
        val selector = Selector.open()

        val serverChannel = ServerSocketChannel.open()

        serverChannel.bind(InetSocketAddress(12345))

        serverChannel.configureBlocking(false)

        serverChannel.register(selector, SelectionKey.OP_ACCEPT)

        logger.info("Сервер запущен на порту 12345")

        while (true) {
            selector.select()

            val keys = selector.selectedKeys().iterator()

            while (keys.hasNext()) {
                val key = keys.next()
                keys.remove()

                if (key.isAcceptable) {
                    val client = serverChannel.accept()

                    logger.info("Новое подключение: ${client.remoteAddress}")

                    client.configureBlocking(true)

                    handleClient(client)
                }
            }
        }
    }

    private fun handleClient(channel: SocketChannel) {

        val socket = channel.socket()

        val output = ObjectOutputStream(socket.getOutputStream())
        output.flush()

        val input = ObjectInputStream(socket.getInputStream())

        val request = input.readObject() as Request

        logger.info("Получен запрос: ${request.commandName}")

        if (request.commandName == "connect") {

            val response = Response(
                message = "Команды получены",
                commands = commandManager.getCommandInfo()
            )

            output.writeObject(response)

            output.flush()

            socket.close()

            return
        }

        val response = commandManager.execute(request)

        val responseWithCommands =
            response.copy(
                commands = commandManager.getCommandInfo()
            )

        output.writeObject(responseWithCommands)

        output.flush()

        logger.info("Отправлен ответ: ${request.commandName}")

        socket.close()
    }
}