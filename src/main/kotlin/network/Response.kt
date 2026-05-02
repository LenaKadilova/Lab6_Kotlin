package network

import java.io.Serializable


data class Response(
    val message: String,
    val success: Boolean = true,
    val exit: Boolean = false
) : Serializable