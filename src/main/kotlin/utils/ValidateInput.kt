package utils

import java.util.*

object ValidateInput {

    @JvmStatic
    fun readValidCategory(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (CategoryUtility.isValidCategory(input))
                return input
            else {
                print("Invalid category $input. Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }
}