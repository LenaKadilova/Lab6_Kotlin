package server

import common.Request
import server.CommandManager
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel


class Server(private val collectionManager: CollectionManager, private val commandManager: CommandManager) {

    fun start() {
        val selector = Selector.open()
        val serverChannel = ServerSocketChannel.open()
        serverChannel.bind(InetSocketAddress(12345))
        serverChannel.configureBlocking(false)          // неблокирующий режим!
        serverChannel.register(selector, SelectionKey.OP_ACCEPT)
        println("Сервер запущен на порту 12345")

        while (true) {
            selector.select()
            val keys = selector.selectedKeys().iterator()
            while (keys.hasNext()) {
                val key = keys.next()
                keys.remove()
                when {
                    key.isAcceptable -> {
                        val client = serverChannel.accept()
                        client.configureBlocking(true)  // для ObjectInputStream удобнее
                        handleClient(client)
                    }
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
        val response = commandManager.execute(request)
        output.writeObject(response)
        socket.close()
    }
}