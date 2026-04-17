class Client(private val host: String, private val port: Int) {

    fun send(request: Request): Response {
        val socket = Socket(host, port)

        val out = ObjectOutputStream(socket.getOutputStream())
        val input = ObjectInputStream(socket.getInputStream())

        out.writeObject(request)

        val response = input.readObject() as Response

        socket.close()

        return response
    }
}