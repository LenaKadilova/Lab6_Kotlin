package common

import java.io.Serializable

data class Response(
    val message: String,
    val lines: List<String>? = null,
    val commands: Map<String, String> = emptyMap()
) : Serializable