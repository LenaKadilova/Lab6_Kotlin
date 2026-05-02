import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import common.model.*
import common.exceptions.ValidationException
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
}