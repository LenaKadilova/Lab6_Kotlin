class Server(private val commandManager: CommandManager) {

    fun start() {
        val server = ServerSocket(12345)
        println("Сервер запущен")

        while (true) {
            val socket = server.accept()

            val input = ObjectInputStream(socket.getInputStream())
            val output = ObjectOutputStream(socket.getOutputStream())

            val request = input.readObject() as Request

            val response = commandManager.execute(request)

            output.writeObject(response)

            socket.close()
        }
    }
}