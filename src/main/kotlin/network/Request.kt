package network

import model.Dragon
import java.io.Serializable

data class Request(
    val commandName: String,
    val args: List<String> = emptyList(),
    val dragon: Dragon? = null
) : Serializable