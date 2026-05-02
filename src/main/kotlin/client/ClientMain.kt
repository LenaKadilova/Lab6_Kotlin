package client

fun main() {
    val client = SimpleClient("localhost", 5556)
    client.start()
}