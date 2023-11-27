import controller.SchoolAPI
import model.Grade
import model.Staff
import model.Student
import model.Teacher
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val logger = KotlinLogging.logger {}
private val schoolAPI = SchoolAPI(XMLSerializer(File("school.xml")))

private const val ANSI_RESET = "\u001B[0m"
private const val ANSI_CYAN = "\u001B[36m"
private const val ANSI_GREEN = "\u001B[32m"
private const val ANSI_RED = "\u001B[31m"

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
        val menu = """ 
         > -----------------------------------
> |      SCHOOL DATABASE APP        |
> -----------------------------------
> | SCHOOL MENU                     |
>          |   ${ANSI_CYAN}1)   Add An Entry${ANSI_RESET}             |
>          |   ${ANSI_CYAN}2)   List Entry${ANSI_RESET}               |
>          |   ${ANSI_CYAN}3)   Count Entry${ANSI_RESET}              |
>          |   ${ANSI_CYAN}4)   Details${ANSI_RESET}                  |
>          |   ${ANSI_CYAN}5)   Placeholder${ANSI_RESET}              |
> -----------------------------------
>          |   ${ANSI_GREEN}20) Save Entries${ANSI_RESET}              |
>          |   ${ANSI_GREEN}21) Load Entries${ANSI_RESET}              |
>           |   ${ANSI_RED}0) Exit${ANSI_RESET}                        |
> -----------------------------------
> ==>> """.trimMargin(">")

    val terminalWidth = 400
    val centeredMenu = menu.lines().joinToString("\n") { line ->
        val spacesNeeded = (terminalWidth - line.length) / 2
        " ".repeat(spacesNeeded) + line
    }

    return readNextInt(centeredMenu)
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> add()
            2 -> list()
            3 -> count()
            4 -> details()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun add() {
    if (1 > 0) {
        val subMenu = """
                  > --------------------------------
                 > |   1)   Add Staff details       |
                 > |   2)   Add Student details     |
                 > |   3)   Add student grades      |
                 > |   4)   Add Teacher details     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        val terminalWidth = 400
        val centeredSubMenu = subMenu.lines().joinToString("\n") { line ->
            val spacesNeeded = (terminalWidth - line.length) / 2
            " ".repeat(spacesNeeded) + line
        }

        val option = readNextInt(centeredSubMenu)

        when (option) {
            1 -> addStaff()
            2 -> addStudent()
            3 -> addGrades()
            4 -> addTeacher()
            else -> println("Invalid option entered: $option");
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}


fun list() {
    if (1 > 0) {
        val subMenu = """
                  > --------------------------------
                 > |   1)   List Staff details     |
                 > |   2)   List Student details   |
                 > |   3)   List student grades    |
                 > |   4)   List Teacher details   |
                  > --------------------------------
         > ==>> """.trimMargin(">")

        val terminalWidth = 400
        val centeredSubMenu = subMenu.lines().joinToString("\n") { line ->
            val spacesNeeded = (terminalWidth - line.length) / 2
            " ".repeat(spacesNeeded) + line
        }

        val option = readNextInt(centeredSubMenu)

        when (option) {
            1 -> listStaff()
            2 -> listStudent()
            3 -> listGrade()
            4 -> listTeacher()
            else -> println("Invalid option entered: $option");
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}

fun count() {
    if (1 > 0) {
        val subMenu = """
                  > --------------------------------
                 > |   1)   Count Staff details    |
                 > |   2)   Count Student details  |
                 > |   3)   Count student grades   |
                 > |   4)   Count Teacher details  |
                  > --------------------------------
         > ==>> """.trimMargin(">")

        val terminalWidth = 400
        val centeredSubMenu = subMenu.lines().joinToString("\n") { line ->
            val spacesNeeded = (terminalWidth - line.length) / 2
            " ".repeat(spacesNeeded) + line
        }

        val option = readNextInt(centeredSubMenu)

        when (option) {
            1 -> countStaff()
            2 -> countStudent()
            3 -> countGrade()
            4 -> countTeacher()
            else -> println("Invalid option entered: $option");
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}

fun details() {
    if (1 > 0) {
        val subMenu = """
                  > --------------------------------
                 > |   1)   Staff details          |
                 > |   2)   Student details        |
                 > |   3)   Teacher details        |
                  > --------------------------------
         > ==>> """.trimMargin(">")

        val terminalWidth = 400
        val centeredSubMenu = subMenu.lines().joinToString("\n") { line ->
            val spacesNeeded = (terminalWidth - line.length) / 2
            " ".repeat(spacesNeeded) + line
        }

        val option = readNextInt(centeredSubMenu)

        when (option) {
            1 -> countStaff()
            2 -> countStudent()
            3 -> countGrade()
            else -> println("Invalid option entered: $option");
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}

fun printCentered(text: String) {
    val terminalWidth = 400
    val spacesNeeded = (terminalWidth - text.length) / 2
    val centeredText = " ".repeat(spacesNeeded) + text
    println(centeredText)
}

fun readNextLineCentered(prompt: String): String {
    printCentered(prompt)
    return readLine() ?: ""
}

fun readNextIntCentered(prompt: String): Int {
    printCentered(prompt)
    return readLine()?.toIntOrNull() ?: 0
}

fun addStaff(){
    val staffName = readNextLineCentered("Enter Staff name: ")
    val staffId = readNextIntCentered("Enter staff ID: ")
    val staffAddress = readNextLineCentered("Enter the staff address: ")
    val staffPhone = readNextIntCentered("Enter the staff members phone number: ")
    val staffType = readNextIntCentered("Type of staff: 1 for teacher & 0 for other: ")

    val isAdded : Boolean = schoolAPI.addStaff(Staff(staffName, staffId, staffAddress,
                                                     staffPhone, staffType))

    if (isAdded){
        val successMessage = "Staff added successfully"
        printCentered(successMessage)
    } else{
        val errorMessage = "Failed to add student details"
        printCentered(errorMessage)
    }
}

fun addStudent(){
    val studentName = readNextLine("Enter the full name of the student: ")
    val studentId = readNextInt("Enter the students ID: ")
    val studentYear = readNextInt("Enter the year the student is in: ")
    val studentAddress = readNextLine("Enter the address of the student: ")
    val studentRecord = readNextLine("Enter the students records: ")

    val isAdded : Boolean = schoolAPI.addStudent(Student(studentName, studentId, studentYear,
                                        studentAddress, studentRecord))
    if (isAdded){
        println("Student added successfully")
    } else{
        println("Failed to add student details")
    }
}

fun addGrades(){
    val English = readNextInt("Enter the students grade for English: ")
    val Maths = readNextInt("Enter the students grade for Maths: ")
    val Geography = readNextInt("Enter the students grade for Geography: ")
    val History = readNextInt("Enter the students grade for History: ")
    val Civics = readNextInt("Enter the students grade for Civics: ")
    val Irish = readNextInt("Enter the students grade for Irish: ")

    val isAdded : Boolean = schoolAPI.addGrade(Grade(English, Maths, Geography,
        History, Civics, Irish))

    if (isAdded){
        println("Grades added successfully")
    } else{
        println("Failed to add Grade entries")
    }
}

fun addTeacher(){
    val subjectsTeaching = readNextLine("Enter the subjects this teacher teaches: ")
    val classroomNumber = readNextInt("Enter their classroom number: ")
    val yearsWithTheSchool = readNextInt("Enter the number of years with the school: ")
    val title = readNextLine("Enter the teachers official job title: ")
    val childSafety = readNextLine("Enter if this teacher is a child safety officer: ")

    val isAdded : Boolean = schoolAPI.addTeacher(Teacher(subjectsTeaching, classroomNumber,
                                                yearsWithTheSchool, title, childSafety))

    if (isAdded){
        println("Teacher added successfully")
    } else{
        println("Failed to add Teacher details")
    }
}

fun listStaff(){ println(schoolAPI.listAllStaff()) }
fun listStudent(){ println(schoolAPI.listAllStudents()) }
fun listGrade(){ println(schoolAPI.listAllGrades()) }
fun listTeacher(){ print(schoolAPI.listAllTeachers()) }

fun countStaff(){ println(schoolAPI.countAllStaff()) }
fun countStudent(){ println(schoolAPI.countAllStudent()) }
fun countGrade(){ println(schoolAPI.countAllGrade()) }
fun countTeacher(){ println(schoolAPI.countAllTeacher()) }

fun save() {
    try {
        schoolAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        schoolAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    logger.info { "Exiting APP..." }
    System.exit(0)
}