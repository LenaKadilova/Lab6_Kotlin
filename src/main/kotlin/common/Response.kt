package common

import java.io.Serializable

data class Response(
    val message: String,
    val lines: List<String>? = null
) : Serializable