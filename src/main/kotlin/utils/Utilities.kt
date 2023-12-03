package utils

import readNextIntCentered

object Utilities {

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    @JvmStatic
    fun readNextIntWithValidation(prompt: String, min: Int, max: Int): Int {
        var input: Int
        do {
            input = readNextIntCentered(prompt)
            if (validRange(input, min, max)) {
                break
            } else {
                println("Invalid input. Please enter a number between $min and $max.")
            }
        } while (true)
        return input
    }
}