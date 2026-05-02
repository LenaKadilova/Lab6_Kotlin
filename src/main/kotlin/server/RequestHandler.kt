package server

import collection.CommandManager
import network.Request
import network.Response

class RequestHandler(
    private val commandManager: CommandManager
) {

    fun handle(request: Request): Response {

        val result = commandManager.execution(
            request.commandName,
            request.args,
            request.dragon
        )

        return if (result != null) {
            Response(result)
        } else {
            Response(
                message = "Неизвестная команда: ${request.commandName}",
                success = false
            )
        }
    }
}