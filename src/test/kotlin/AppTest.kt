import collection.CollectionManager
import collection.CommandManager
import collection.IOManager
import commands.Command
import commands.RemoveKeyCommand
import commands.SaveCommand
import io.mockk.*
import model.*
import exceptions.ValidationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AppTest {

    private fun createDragon(
        id: Int = 1,
        name: String = "Drako",
        x: Float = 10f,
        y: Long = 20,
        age: Long = 100,
        weight: Double = 50.5,
        type: DragonType = DragonType.FIRE,
        character: DragonCharacter = DragonCharacter.WISE,
        head: DragonHead? = DragonHead(2, 30.0),
        creationDate: LocalDateTime = LocalDateTime.of(2025, 1, 1, 12, 0)
    ): Dragon {
        return Dragon(
            id = id,
            name = name,
            coordinates = Coordinates(x, y),
            creationDate = creationDate,
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = head
        )
    }

    @Test
    fun `Dragon with blank name throws ValidationException`() {
        assertThrows(ValidationException::class.java) {
            createDragon(name = "   ")
        }
    }

    @Test
    fun `Dragon with non positive age throws ValidationException`() {
        assertThrows(ValidationException::class.java) {
            createDragon(age = 0)
        }
    }

    @Test
    fun `Dragon with non positive weight throws ValidationException`() {
        assertThrows(ValidationException::class.java) {
            createDragon(weight = 0.0)
        }
    }

    @Test
    fun `Coordinates with x greater than 523 throws ValidationException`() {
        assertThrows(ValidationException::class.java) {
            Coordinates(600f, 10)
        }
    }

    @Test
    fun `removeByKey removes existing element and prints message`() {
        val io = mockk<IOManager>(relaxed = true)
        val manager = CollectionManager(LocalDateTime.now(), "test.json", io)

        manager.storage[1L] = createDragon()

        manager.removeByKey(1L)

        assertFalse(manager.storage.containsKey(1L))
        verify { io.println("Элемент удалён") }
    }

    @Test
    fun `removeByKey with missing key prints not found message`() {
        val io = mockk<IOManager>(relaxed = true)
        val manager = CollectionManager(LocalDateTime.now(), "test.json", io)

        manager.removeByKey(99L)

        verify { io.println("Ключ не найден") }
    }

    @Test
    fun `updateById keeps old id and old creationDate`() {
        val io = mockk<IOManager>(relaxed = true)
        val manager = CollectionManager(LocalDateTime.now(), "test.json", io)

        val oldDate = LocalDateTime.of(2024, 5, 10, 10, 0)
        val oldDragon = createDragon(id = 7, creationDate = oldDate, name = "Old")
        manager.storage[100L] = oldDragon

        val newDragon = createDragon(
            id = 999,
            creationDate = LocalDateTime.now(),
            name = "New"
        )

        manager.updateById(7L, newDragon)

        val updated = manager.storage[100L]!!
        assertEquals(7, updated.id)
        assertEquals(oldDate, updated.creationDate)
        assertEquals("New", updated.name)
        verify { io.println("Элемент обновлён") }
    }

    @Test
    fun `CommandManager execution returns true for existing command`() {
        val commandManager = CommandManager()
        val command = mockk<Command>()

        every { command.name } returns "test"
        every { command.description } returns "desc"
        every { command.execution(any()) } just Runs

        commandManager.addToList(command)
        val result = commandManager.execution("test", listOf("arg1"))

        assertTrue(result)
        verify { command.execution(listOf("arg1")) }
    }

    @Test
    fun `CommandManager execution returns false for unknown command`() {
        val commandManager = CommandManager()

        val result = commandManager.execution("unknown", emptyList())

        assertFalse(result)
    }

    @Test
    fun `SaveCommand with empty args prints error and does not save`() {
        val io = mockk<IOManager>(relaxed = true)
        val manager = mockk<CollectionManager>(relaxed = true)
        val command = SaveCommand(manager, io)

        command.execution(emptyList())

        verify { io.println("Ошибка: нужно указать имя файла") }
        verify(exactly = 0) { manager.save(any()) }
    }

    @Test
    fun `RemoveKeyCommand with invalid key prints error`() {
        val io = mockk<IOManager>(relaxed = true)
        val manager = mockk<CollectionManager>(relaxed = true)
        val command = RemoveKeyCommand(manager, io)

        command.execution(listOf("abc"))

        verify { io.println("Ключ должен быть числом") }
        verify(exactly = 0) { manager.removeByKey(any()) }
    }
}