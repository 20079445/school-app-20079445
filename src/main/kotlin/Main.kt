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

val serializer = XMLSerializer(File("Staff.xml"), File("Student.xml"))

val schoolAPI = SchoolAPI(serializer)

private const val ANSI_RESET = "\u001B[0m"
private const val ANSI_CYAN = "\u001B[36m"
private const val ANSI_GREEN = "\u001B[32m"
private const val ANSI_RED = "\u001B[31m"

var width = 100

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
>          |   ${ANSI_CYAN}5)   Delete Entry${ANSI_RESET}             |
>          |   ${ANSI_CYAN}6)   TimeTable${ANSI_RESET}                |
> -----------------------------------
>          |   ${ANSI_GREEN}20) Save Entries${ANSI_RESET}              |
>          |   ${ANSI_GREEN}21) Load Entries${ANSI_RESET}              |
>           |   ${ANSI_RED}0) Exit${ANSI_RESET}                        |
> -----------------------------------
> ==>> """.trimMargin(">")

    val terminalWidth = width
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
            5 -> delete()
            6 -> timetable()
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
        val terminalWidth = width
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

        val terminalWidth = width
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

        val terminalWidth = width
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

        val terminalWidth = width
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

fun delete() {
    if (1 > 0) {
        val subMenu = """
                  > --------------------------------
                 > |   1)   Delete Staff           |
                 > |   2)   Delete Student         |
                 > |   3)   Delete Grades          |
                 > |   4)   Delete Teacher         |
                  > --------------------------------
         > ==>> """.trimMargin(">")

        val terminalWidth = width
        val centeredSubMenu = subMenu.lines().joinToString("\n") { line ->
            val spacesNeeded = (terminalWidth - line.length) / 2
            " ".repeat(spacesNeeded) + line
        }

        val option = readNextInt(centeredSubMenu)

        when (option) {
            1 -> deleteStaff()
            2 -> deleteStudent()
            3 -> deleteGrade()
            4 -> deleteTeacher()
            else -> println("Invalid option entered: $option");
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}

fun printCentered(text: String) {
    val terminalWidth = width
    val spacesNeeded = maxOf(0, (terminalWidth - text.length) / 2)
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
        schoolAPI.isAddStaffUsed = true
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
    val studentClass = readNextIntCentered("Enter the class the student is in: ")
    val studentAddress = readNextLineCentered("Enter the address of the student: ")
    val studentRecord = readNextLineCentered("Enter the students records: ")

    val isAdded : Boolean = schoolAPI.addStudent(Student(studentName, studentId, studentYear,
                                                studentClass, studentAddress, studentRecord))
    if (isAdded){
        schoolAPI.isAddStudentUsed = true
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
        schoolAPI.isAddGradeUsed = true
        println("Grades added successfully")
    } else{
        println("Failed to add Grade entries")
    }
}

fun addTeacher(){
    val teacherId = readNextIntCentered("Enter the ID of the teacher: ")
    val subjectsTeaching = readNextLine("Enter the subjects this teacher teaches: ")
    val classroomNumber = readNextIntCentered("Enter their classroom number: ")
    val classesAssigned = readNextInt("Enter the classes you are assigned: ")
    val yearsWithTheSchool = readNextIntCentered("Enter the number of years with the school: ")
    val title = readNextLineCentered("Enter the teachers official job title: ")
    val childSafety = readNextLineCentered("Enter if this teacher is a child safety officer: ")

    val isAdded : Boolean = schoolAPI.addTeacher(Teacher(teacherId, subjectsTeaching, classroomNumber,
                                                        classesAssigned, yearsWithTheSchool, title, childSafety))

    if (isAdded){
        schoolAPI.isAddTeacherUsed = true
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
            val studentName = readNextLineCentered("Enter the full name of the student: ")
            val studentId = readNextIntCentered("Enter the students ID: ")
            val studentYear = readNextIntCentered("Enter the year the student is in: ")
            val studentClass = readNextIntCentered("Enter the class the student is in: ")
            val studentAddress = readNextLineCentered("Enter the address of the student: ")
            val studentRecord = readNextLineCentered("Enter the students records: ")

            if (schoolAPI.updateStudent(studentToUpdate, Student(studentName, studentId, studentYear, studentClass,
                                        studentAddress, studentRecord))){
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
        val studentId = readNextIntCentered("Enter the ID of the student you want to assign their grades to: ")
        val English = readNextIntCentered("Enter the students grade for English: ")
        val Maths = readNextIntCentered("Enter the students grade for Maths: ")
        val Geography = readNextIntCentered("Enter the students grade for Geography: ")
        val History = readNextIntCentered("Enter the students grade for History: ")
        val Civics = readNextIntCentered("Enter the students grade for Civics: ")
        val Irish = readNextIntCentered("Enter the students grade for Irish: ")

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
            val teacherId = readNextIntCentered("Enter the ID of the teacher: ")
            val subjectsTeaching = readNextLineCentered("Enter the subjects this teacher teaches: ")
            val classroomNumber = readNextIntCentered("Enter their classroom number: ")
            val classesAssigned = readNextIntCentered("Enter the classes you are assigned: ")
            val yearsWithTheSchool = readNextIntCentered("Enter the number of years with the school: ")
            val title = readNextLineCentered("Enter the teachers official job title: ")
            val childSafety = readNextLineCentered("Enter if this teacher is a child safety officer: ")

            if (schoolAPI.updateTeacher(teacherToUpdate, Teacher(teacherId, subjectsTeaching, classroomNumber,
                                                            classesAssigned,yearsWithTheSchool, title, childSafety))){
                println("Teacher updated successfully")
            } else{
                println("Failed to update Teacher details")
            }
        } } }

fun deleteStaff(){
    listStaff()
    if (schoolAPI.countAllStaff() > 0){
        val IdToDelete = readNextIntCentered("Enter the ID of the entry you want to delete: ")

        val staffToDelete = schoolAPI.deleteStaff(IdToDelete)
        if (staffToDelete != null){
            println("Delete Successful! Deleted Staff entry: ${staffToDelete}")
        } else {
            println("Delete not successful")
        }
    } }

fun deleteStudent(){
    listStudent()
    if (schoolAPI.countAllStudent() > 0){
        val IdToDelete = readNextIntCentered("Enter the ID of the entry you want to delete: ")

        val studentToDelete = schoolAPI.deleteStudent(IdToDelete)
        if (studentToDelete != null){
            println("Delete Successful! Deleted Student entry: ${studentToDelete}")
        } else {
            println("Delete not successful")
        }
    } }

fun deleteGrade(){
    listGrade()
    if (schoolAPI.countAllGrade() > 0){
        val IdToDelete = readNextIntCentered("Enter the ID of the entry you want to delete: ")

        val gradeToDelete = schoolAPI.deleteGrade(IdToDelete)
        if (gradeToDelete != null){
            println("Delete Successful! Deleted Grade entry: ${gradeToDelete}")
        } else {
            println("Delete not successful")
        }
    } }

fun deleteTeacher(){
    listTeacher()
    if (schoolAPI.countAllTeacher() > 0){
        val IdToDelete = readNextIntCentered("Enter the ID of the entry you want to delete: ")

        val teacherToDelete = schoolAPI.deleteTeacher(IdToDelete)
        if (teacherToDelete != null){
            println("Delete Successful! Deleted Teacher entry: ${teacherToDelete}")
        } else {
            println("Delete not successful")
        }
    } }

fun timetable(): Any {
    val studentId = readNextIntCentered("Enter your student ID to view your timetable: ")
    val student = schoolAPI.findStudent(studentId)

    return if (student != null) {
        val staffInfo = schoolAPI.listAllStaff()

        val timetableResult = schoolAPI.generateTimetable(student, staffInfo)
        return printCentered(timetableResult)
    } else {
        "Invalid ID entered"
    }
}


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