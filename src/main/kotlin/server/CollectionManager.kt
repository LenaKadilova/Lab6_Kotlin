package collection

import model.Dragon
import java.time.LocalDateTime
import java.util.Hashtable
import com.google.gson.GsonBuilder
import java.io.FileWriter
import model.Coordinates
import model.DragonCharacter
import model.DragonHead
import model.DragonType
import java.io.File

/**
 * Класс для управления коллекцией объектов Dragon.
 * Отвечает за хранение, изменение и обработку элементов коллекции,
 * а также взаимодействие с пользователем через IOManager.
 *
 * @property time время инициализации коллекции
 * @property fileName имя файла, связанного с коллекцией
 * @property io объект для ввода и вывода данных
 */
class CollectionManager (val time: LocalDateTime, val fileName: String, private val io: IOManager) {
    val storage: Hashtable<Long, Dragon> = Hashtable()
    /**
     * Возвращает количество элементов в коллекции.
     * @return размер коллекции
     */
    fun Size(): Int = storage.size
    /**
     * Выводит все элементы коллекции.
     */
    fun ShowAll(): List<String> {
        if (storage.isEmpty()) {
            return listOf("Коллекция пустая")
        }

        val result = mutableListOf<String>()

        for (entry in storage.entries) {
            result.add("Ключ = ${entry.key}")
            result.add("Значение = ${entry.value}")
        }
        return result
    }

    fun clear() {
        storage.clear()
    }
    /**
     * Удаляет элемент по заданному ключу.
     * @param key ключ элемента
     */
    fun removeByKey(key: Long): String {
        return if (storage.containsKey(key)) {
            storage.remove(key)
            "Элемент удалён"
        } else {
            "Ключ не найден"
        }
    }
    /**
     * Выводит элементы коллекции, отсортированные по выбранному параметру.
     */
    fun printAscending(param: String): List<String> {
        if (storage.isEmpty()) {
            return listOf("Элементы не найдены")
        }

        val sortedList = when (param) {
            "id" -> storage.values.sortedBy { it.id }
            "x" -> storage.values.sortedBy { it.coordinates.x }
            "y" -> storage.values.sortedBy { it.coordinates.y }
            "creationDate" -> storage.values.sortedBy { it.creationDate }
            "age" -> storage.values.sortedBy { it.age }
            "weight" -> storage.values.sortedBy { it.weight }
            "eyesCount" -> storage.values.sortedBy { it.head?.eyesCount }
            "toothCount" -> storage.values.sortedBy { it.head?.toothCount }
            else -> return listOf("Неверный параметр")
        }

        return sortedList.map { it.toString() }
    }
    /**
     * Фильтрует элементы по префиксу имени.
     * @param prefix начало имени
     */
    fun filterStartsWithName(prefix: String): List<String> {
        val filtered = storage.values.filter { it.name.startsWith(prefix) }

        return if (filtered.isEmpty()) {
            listOf("Элементы не найдены")
        } else {
            filtered.map { it.toString() }
        }
    }
    /**
     * Группирует элементы по id и выводит количество в каждой группе.
     */
    fun groupCountingById(): List<String> {
        val grouped = storage.values.groupingBy { it.id }.eachCount()

        return if (grouped.isEmpty()) {
            listOf("Коллекция пуста")
        } else {
            grouped.map { (id, count) -> "ID: $id -> количество: $count" }
        }
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, FileManager.LocalDateTimeAdapter())
        .setPrettyPrinting()
        .create()
    /**
     * Сохраняет коллекцию в файл.
     * @param fileName имя файла
     */
    fun save(fileName: String) {
        try {
            val fileWriter = FileWriter(fileName)
            gson.toJson(storage, fileWriter)
            fileWriter.close()
            io.println("Коллекция сохранена в файл: $fileName")
        } catch (e: Exception) {
            io.println("Ошибка сохранения: ${e.message}")
        }
    }
    /**
     * Загружает коллекцию из файла.
     * @param fileManager менеджер работы с файлом
     */
    fun loadCollectionFromFile(fileManager: FileManager) {
        val elements = fileManager.readCollection()
        for ((key, dragon) in elements) {
            storage[key] = dragon.copy(id = nextId())
        }
    }

    private var nextId: Int = 1
    fun size(): Int = storage.size
    fun nextId(): Int {
        val id = nextId
        nextId++
        return id
    }
    /**
     * Считывает значение типа Long с консоли.
     * @param message сообщение пользователю
     * @return введённое значение
     */
    fun readLong(message: String): Long {
        while (true) {
            io.println(message)
            try {
                var value = io.readLine().toLong()
                return value
            } catch (e: NumberFormatException) {
                println("Неверный ввод, должно быть число типа Long")
            }

        }
    }
    /**
     * Считывает значение типа Int с консоли.
     * @param message сообщение пользователю
     * @return введённое значение
     */
    fun readInt(message: String): Int {
        while (true) {
            io.println(message)
            try {
                var value = io.readLine().toInt()
                return value
            } catch (e: NumberFormatException) {
                io.println("Неверный ввод, должно быть число типа Int")
            }

        }
    }
    /**
     * Считывает значение типа Float с консоли.
     * @param message сообщение пользователю
     * @return введённое значение
     */
    fun readFloat(message: String): Float {
        while (true) {
            io.println(message)
            try {
                var value = io.readLine().toFloat()
                return value
            } catch (e: NumberFormatException) {
                io.println("Неверный ввод, должно быть число типа Float")
            }
        }
    }
    /**
     * Считывает значение типа Double с консоли.
     * @param message сообщение пользователю
     * @return введённое значение
     */
    fun readDouble(message: String): Double {
        while (true) {
            io.println(message)
            try {
                var value = io.readLine().toDouble()
                return value
            } catch (e: NumberFormatException) {
                io.println("Неверный ввод, должно быть число типа Double")
            }

        }
    }
    /**
     * Обновляет элемент по его id.
     * @param id идентификатор элемента
     * @param newDragon новый объект
     */
    fun updateById(id: Long, newDragon: Dragon) {
        var keyToUpdate: Long? = null
        var oldDragon: Dragon? = null

        for ((key, dragon) in storage) {
            if (dragon.id.toLong() == id) {
                keyToUpdate = key
                oldDragon = dragon
                break
            }
        }

        if (keyToUpdate == null || oldDragon == null) {
            io.println("Элемент с таким id не найден")
            return
        }

        val updatedDragon = newDragon.copy(
            id = oldDragon.id,
            creationDate = oldDragon.creationDate
        )

        storage[keyToUpdate] = updatedDragon
        io.println("Элемент обновлён")
    }

    /**
     * Создаёт объект Dragon на основе пользовательского ввода.
     * @param id идентификатор
     * @return созданный объект Dragon
     */
    fun createDragon(id: Int): Dragon {

        io.println("Введите имя")
        val nameDragon = io.readLine()

        val x = readFloat("Введите коррдинату x")
        val y = readLong("Введите коррдинату y")
        val age = readLong("Введите возраст")
        val weight = readDouble("Введите вес")

        var type: DragonType
        while (true) {
            io.println("Выберете тип дракона: water,underground, air, fire")
            try {
                type = DragonType.valueOf(io.readLine().uppercase())
                break
            } catch (e: Exception) {
                io.println("Неверный тип дракона")
            }
        }

        var character: DragonCharacter
        while (true) {
            io.println("Выберете характер дракона: wise, good, chaotic, chaotic_evil, fickle")
            try {
                character = DragonCharacter.valueOf(io.readLine().uppercase())
                break
            } catch (e: Exception) {
                io.println("Неверный характер дракона")
            }
        }

        io.println("Создать голову? yes/no")
        val answer = io.readLine().lowercase()
        val head: DragonHead?
        if (answer == "yes") {
            val eyesCount = readInt("Введите количество глаз")
            val toothCount = readDouble("Введите количество зубов")
            head  = DragonHead(eyesCount, toothCount)
        }
        else {
            head = null
        }

        return Dragon(
            id = id,
            name = nameDragon,
            coordinates = Coordinates(x, y),
            creationDate = LocalDateTime.now(),
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = head
        )
    }
    /**
     * Удаляет элементы с ключом больше заданного.
     * @param key ключ для сравнения
     */
    fun removeGreaterKey(key: Long): String {
        val keysToRemove = storage.keys.filter { it > key }

        if (keysToRemove.isEmpty()) {
            return "Нет элементов с ключом больше этого"
        }

        keysToRemove.forEach { storage.remove(it) }
        return "Удалено элементов: ${keysToRemove.size}"
    }
    /**
     * Удаляет элементы, значение которых больше заданного по выбранному параметру.
     */
    fun removeGreater(param: String, value: Double): String {
        if (storage.isEmpty()) {
            return "Коллекция пуста"
        }

        val fieldMap: Map<String, (Dragon) -> Double> = mapOf(
            "id" to { it.id.toDouble() },
            "x" to { it.coordinates.x.toDouble() },
            "y" to { it.coordinates.y.toDouble() },
            "toothCount" to { it.head?.toothCount ?: 0.0 },
            "age" to { it.age.toDouble() },
            "weight" to { it.weight },
            "eyesCount" to { (it.head?.eyesCount ?: 0).toDouble() }
        )

        val selector = fieldMap[param] ?: return "Неверный параметр"

        val keysToRemove = storage
            .filter { (_, dragon) -> selector(dragon) > value }
            .keys

        if (keysToRemove.isEmpty()) {
            return "Нет элементов, превышающих заданное значение"
        }

        keysToRemove.forEach { storage.remove(it) }

        return "Удалено элементов: ${keysToRemove.size}"
    }
    /**
     * Заменяет значение элемента, если новое больше текущего.
     */
    fun replaceIfGreater(key: Long, param: String, value: Double): String {

        if (storage.isEmpty()) {
            return "Коллекция пуста"
        }

        val current = storage[key] ?: return "Элемент с таким ключом не найден"

        var isReplaced = false

        when (param) {
            "x" -> {
                if (value > current.coordinates.x) {
                    current.coordinates.x = value.toFloat()
                    isReplaced = true
                }
            }
            "y" -> {
                if (value > current.coordinates.y) {
                    current.coordinates.y = value.toLong()
                    isReplaced = true
                }
            }
            "age" -> {
                if (value > current.age) {
                    current.age = value.toLong()
                    isReplaced = true
                }
            }
            "weight" -> {
                if (value > current.weight) {
                    current.weight = value
                    isReplaced = true
                }
            }
            "eyesCount" -> {
                val head = current.head ?: return "У элемента нет головы"
                if (value > head.eyesCount) {
                    head.eyesCount = value.toInt()
                    isReplaced = true
                }
            }
            "toothCount" -> {
                val head = current.head ?: return "У элемента нет головы"
                if (value > head.toothCount) {
                    head.toothCount = value
                    isReplaced = true
                }
            }
            else -> return "Неверный параметр"
        }

        return if (isReplaced) {
            "Элемент успешно заменён"
        } else {
            "Новое значение не больше старого, замена не выполнена"
        }
    }
    /**
     * Выполняет команды из файла.
     * Защищает от рекурсивного вызова скриптов.
     *
     * @param fileName имя файла со скриптом
     */
    fun executeScript(fileName: String) {

        if (executingScripts.contains(fileName)) {
            io.println("Обнаружена рекурсия! Скрипт уже выполняется.")
            return
        }

        val file = File(fileName)

        if (!file.exists() || !file.isFile) {
            io.println("Файл не найден: $fileName")
            return
        }

        executingScripts.add(fileName)

        io.setFileInput(file)
    }


}
