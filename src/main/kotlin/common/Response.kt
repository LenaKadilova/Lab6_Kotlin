data class Response(
    val message: String,
    val dragons: List<Dragon>?
) : Serializable