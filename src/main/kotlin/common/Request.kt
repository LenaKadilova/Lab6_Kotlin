data class Request(
    val commandName: String,
    val argument: String?,
    val dragon: Dragon?
) : Serializable