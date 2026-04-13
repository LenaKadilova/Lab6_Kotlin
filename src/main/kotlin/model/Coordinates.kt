package model

import exceptions.ValidationException
/**
 * Класс координат.
 *
 * @property x координата по оси X (максимум 523)
 * @property y координата по оси Y (не может быть null)
 *
 * @throws ValidationException если x > 523
 */
data class Coordinates(
    var x: Float,   // максимум 523
    var y: Long     // не null
) {
    init {
        if (x > 523) {
            throw ValidationException("Координата x должна быть <= 523")
        }
    }
}