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
private val schoolAPI = SchoolAPI(XMLSerializer(File("student.xml")),
                                  XMLSerializer(File("Staff.xml")),
                                  XMLSerializer(File("Grade.xml")),
                                  XMLSerializer(File("Teacher.xml")))

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
>          |   ${ANSI_CYAN}4)   Update Details${ANSI_RESET}           |
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
            4 -> update()
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

fun update() {
    if (1 > 0) {
        val subMenu = """
                  > --------------------------------
                 > |   1)   Update Staff           |
                 > |   2)   Update Student         |
                 > |   3)   Update Grades          |
                 > |   4)   Update Teacher         |
                  > --------------------------------
         > ==>> """.trimMargin(">")

        val terminalWidth = 400
        val centeredSubMenu = subMenu.lines().joinToString("\n") { line ->
            val spacesNeeded = (terminalWidth - line.length) / 2
            " ".repeat(spacesNeeded) + line
        }

        val option = readNextInt(centeredSubMenu)

        when (option) {
            1 -> updateStaff()
            2 -> updateStudent()
            3 -> updateGrade()
            4 -> updateTeacher()
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
    val input = readLine() ?: ""
    printCentered("> $input")
    return input
}

fun readNextIntCentered(prompt: String): Int {
    printCentered(prompt)
    val input = readLine()?.toIntOrNull() ?: 0
    printCentered("> $input")
    return input
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
        val errorMessage = "Failed to add staff details"
        printCentered(errorMessage)
    }
}

fun addStudent(){
    val studentName = readNextLineCentered("Enter the full name of the student: ")
    val studentId = readNextIntCentered("Enter the students ID: ")
    val studentYear = readNextIntCentered("Enter the year the student is in: ")
    val studentAddress = readNextLineCentered("Enter the address of the student: ")
    val studentRecord = readNextLineCentered("Enter the students records: ")

    val isAdded : Boolean = schoolAPI.addStudent(Student(studentName, studentId, studentYear,
                                        studentAddress, studentRecord))
    if (isAdded){
        println("Student added successfully")
    } else{
        println("Failed to add student details")
    }
}

fun addGrades(){
    val studentId = readNextIntCentered("Enter the ID of the student you want to assign their grades to: ")
    val English = readNextIntCentered("Enter the students grade for English: ")
    val Maths = readNextIntCentered("Enter the students grade for Maths: ")
    val Geography = readNextIntCentered("Enter the students grade for Geography: ")
    val History = readNextIntCentered("Enter the students grade for History: ")
    val Civics = readNextIntCentered("Enter the students grade for Civics: ")
    val Irish = readNextIntCentered("Enter the students grade for Irish: ")

    val isAdded : Boolean = schoolAPI.addGrade(Grade(studentId, English, Maths, Geography,
        History, Civics, Irish))

    if (isAdded){
        println("Grades added successfully")
    } else{
        println("Failed to add Grade entries")
    }
}

fun addTeacher(){
    val teacherId = readNextIntCentered("Enter the ID of the teacher: ")
    val subjectsTeaching = readNextLine("Enter the subjects this teacher teaches: ")
    val classroomNumber = readNextIntCentered("Enter their classroom number: ")
    val yearsWithTheSchool = readNextIntCentered("Enter the number of years with the school: ")
    val title = readNextLineCentered("Enter the teachers official job title: ")
    val childSafety = readNextLineCentered("Enter if this teacher is a child safety officer: ")

    val isAdded : Boolean = schoolAPI.addTeacher(Teacher(teacherId, subjectsTeaching, classroomNumber,
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

fun updateStaff(){
    listStaff()
    if (schoolAPI.countAllStaff() > 0){
        val staffToUpdate = readNextInt("Enter the ID of the staff you wish to update: ")
        if (schoolAPI.isValidStaffId(staffToUpdate)){
            val staffName = readNextLineCentered("Enter Staff name: ")
            val staffId = readNextIntCentered("Enter staff ID: ")
            val staffAddress = readNextLineCentered("Enter the staff address: ")
            val staffPhone = readNextIntCentered("Enter the staff members phone number: ")
            val staffType = readNextIntCentered("Type of staff: 1 for teacher & 0 for other: ")

            if (schoolAPI.updateStaff(staffToUpdate, Staff(staffName, staffId, staffAddress, staffPhone, staffType))){
                val successMessage = "Staff updated successfully"
                printCentered(successMessage)
            } else{
                val errorMessage = "Failed to update staff details"
                printCentered(errorMessage)
            }
        } } }

fun updateStudent(){
    listStudent()
    if (schoolAPI.countAllStudent() > 0){
        val studentToUpdate = readNextInt("Enter the ID of the student you want to update: ")

        if (schoolAPI.isValidStudentId(studentToUpdate)){
            val studentName = readNextLine("Enter the full name of the student: ")
            val studentId = readNextInt("Enter the students ID: ")
            val studentYear = readNextInt("Enter the year the student is in: ")
            val studentAddress = readNextLine("Enter the address of the student: ")
            val studentRecord = readNextLine("Enter the students records: ")

            if (schoolAPI.updateStudent(studentToUpdate, Student(studentName, studentId, studentYear, studentAddress, studentRecord))){
                println("Student updated successfully")
            } else{
                println("Failed to update student details")
            }
        } } }

fun updateGrade(){
    listGrade()
    if (schoolAPI.countAllGrade() > 0){
        val gradeToUpdate = readNextInt("Enter the ID of the students grades you want to update: ")

        if (schoolAPI.isValidGradeId(gradeToUpdate)){
        val studentId = readNextInt("Enter the ID of the student you want to assign their grades to: ")
        val English = readNextInt("Enter the students grade for English: ")
        val Maths = readNextInt("Enter the students grade for Maths: ")
        val Geography = readNextInt("Enter the students grade for Geography: ")
        val History = readNextInt("Enter the students grade for History: ")
        val Civics = readNextInt("Enter the students grade for Civics: ")
        val Irish = readNextInt("Enter the students grade for Irish: ")

        if (schoolAPI.updateGrade(gradeToUpdate, Grade(studentId, English, Maths, Geography, History, Civics, Irish))){
            println("Grades updated successfully")
        } else{
            println("Failed to update Grade entries")
        }
    } } }

fun updateTeacher(){
    listTeacher()
    if (schoolAPI.countAllTeacher() > 0){
        val teacherToUpdate = readNextInt("Enter the ID of the staff teacher you want to update: ")

        if (schoolAPI.isValidTeacherId(teacherToUpdate)){
            val teacherId = readNextInt("Enter the ID of the teacher: ")
            val subjectsTeaching = readNextLine("Enter the subjects this teacher teaches: ")
            val classroomNumber = readNextInt("Enter their classroom number: ")
            val yearsWithTheSchool = readNextInt("Enter the number of years with the school: ")
            val title = readNextLine("Enter the teachers official job title: ")
            val childSafety = readNextLine("Enter if this teacher is a child safety officer: ")

            if (schoolAPI.updateTeacher(teacherToUpdate, Teacher(teacherId, subjectsTeaching, classroomNumber, yearsWithTheSchool, title, childSafety))){
                println("Teacher updated successfully")
            } else{
                println("Failed to update Teacher details")
            }
        }
    }
}

fun save() {
    try {
        schoolAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
        throw e
    }
}

fun load() {
    try {
        schoolAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
        throw e
    }
}

fun exitApp(){
    logger.info { "Exiting APP..." }
    System.exit(0)
}