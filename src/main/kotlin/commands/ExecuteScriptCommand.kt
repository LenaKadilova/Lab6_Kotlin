package commands

import collection.CollectionManager
import collection.CommandManager
import collection.IOManager
/**
 * Команда выполнения скрипта.
 * Считывает команды из файла и выполняет их последовательно.
 */
class ExecuteScriptCommand(
    private val collectionManager: CollectionManager,
    private val io: IOManager
) : Command {

    override val name = "execute_script"
    override val description = "выполнить скрипт из файла"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            io.println("Укажите файл")
            return
        }

        val fileName = args[0]
        collectionManager.executeScript(fileName)
    }
}