package collection

import java.io.File
import java.util.Scanner
import java.io.PrintStream

/**
 * Класс для ввода и вывода данных.
 * Используется для работы с консолью и файлами.
 */
class IOManager {
    private var scanner: Scanner = Scanner(System.`in`)
    private val outStream: PrintStream = PrintStream(System.out, true, "UTF-8")
    private val errStream: PrintStream = PrintStream(System.err, true, "UTF-8")
    /**
     * Выводит сообщение в консоль.
     * @param message текст сообщения
     */
    fun println(message: String) {
        kotlin.io.println(message)
    }
    /**
     * Выводит сообщение в консоль без перевода строки.
     * @param message текст сообщения
     */
    fun print(message: String) {
        outStream.print(message)
    }
    /**
     * Выводит сообщение об ошибке в консоль.
     * @param message текст сообщения
     */
    fun eprintln(message: String) {
        errStream.println(message)
    }
    /**
     * Сбрасывает буфер вывода.
     */
    fun flush() {
        outStream.flush()
    }
    /**
     * Считывает строку из текущего источника ввода.
     * @return введённая строка
     */
    fun readLine(): String {
        return if (scanner.hasNextLine()) scanner.nextLine() else ""
    }
    /**
     * Устанавливает файл как источник ввода.
     * @param file файл
     */
    fun setFileInput(file: File) {
        scanner = Scanner(file)
    }
}