import controller.SchoolAPI
import model.*
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput.readNextInt
import utils.Utilities.readNextIntWithValidation
import utils.ValidateInput.readValidCategory
import java.io.File

private val logger = KotlinLogging.logger {}

//val serializer = XMLSerializer(File("Staff.xml"), File("Student.xml"), File("Grade.xml"), File("Teacher.xml"))
val serializer = JSONSerializer(File("Staff.json"), File("Student.json"), File("Grade.json"), File("Teacher.json"))

val schoolAPI = SchoolAPI(serializer)

private const val ANSI_RESET = "\u001B[0m"
private const val ANSI_CYAN = "\u001B[36m"
private const val ANSI_GREEN = "\u001B[32m"
private const val ANSI_RED = "\u001B[31m"

var width = 200

val registeredUsers = mutableListOf<User>()

/**
 * Main menu where the user can register and login
 *
 */
fun main() {
    showWelcomeMessage()
    val choice = readLine()?.toIntOrNull()

    when (choice) {
        1 -> register()
        2 -> login()
        else -> println("Invalid choice. Please try again.")
    }
}

/**
 * Show welcome message and menu to register and login
 *
 */
fun showWelcomeMessage() {
    printCentered("=== Welcome to the App ===")
    printCentered("1. Register")
    printCentered("2. Login")
    printCentered("Enter your choice: ")
}

/**
 * Register allows the user to register
 *
 */
fun register() {
    printCentered("=== Registration ===")
    printCentered("Enter a username: ")
    val username = readLine().orEmpty()

    printCentered("Enter a password: ")
    val password = readLine().orEmpty()

    val newUser = User(username, password)
    registeredUsers.add(newUser)

    printCentered("Registration successful!\n")
    main()
}

/**
 * Login allows the user to login
 *
 */
fun login() {
    printCentered("=== Login ===")
    printCentered("Enter username: ")
    val enteredUsername = readLine().orEmpty()

    printCentered("Enter password: ")
    val enteredPassword = readLine().orEmpty()

    val isValidCredentials = registeredUsers.any { it.username == enteredUsername && it.password == enteredPassword }

    if (isValidCredentials) {
        println("Login successful!")
        runMenu()
    } else {
        println("Invalid credentials. Please try again.")
    }
}

/**
 * Main menu for the user to use, provides many options to them from adding entries, listing, counting, updating and deleting
 *
 * @return
 */
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
>          |   ${ANSI_CYAN}7)   Report${ANSI_RESET}                   |
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

/**
 * Run menu this is to actually run the functions when they are selected from main menu
 *
 */
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
            7 -> report()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}

/**
 * Add submenu for the user to then navigate to what entry they want to add
 *
 */
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


/**
 * List submenu to allow the user to view any lists of the entries he wants
 *
 */
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

/**
 * Count submenu allows the user to count the number of entries
 *
 */
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

/**
 * Update submenu allows the user to choose what entry they want to update
 *
 */
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

/**
 * Delete submenu allows the navigate to the type of entry they want to delete
 *
 */
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

/**
 * Prints the given text in a centered format based on the terminal width.
 *
 * This function calculates the number of spaces needed to center the text and adds
 * them to the beginning of the text before printing it to the console.
 *
 * @param text The text to be displayed in a centered format.
 */
fun printCentered(text: String) {
    val terminalWidth = width
    val spacesNeeded = maxOf(0, (terminalWidth - text.length) / 2)
    val centeredText = " ".repeat(spacesNeeded) + text
    println(centeredText)
}

/**
 * Reads a line of text from the user in a centered prompt.
 *
 * This function displays the specified prompt in a centered format using [printCentered] and
 * reads a line of text input from the user. If the user provides no input, an empty string is returned.
 *
 * @param prompt The message to be displayed as the prompt.
 * @return The line of text entered by the user or an empty string if no input is provided.
 */
fun readNextLineCentered(prompt: String): String {
    printCentered(prompt)
    val input = readLine() ?: ""
    printCentered("> $input")
    return input
}

/**
 * Reads an integer from the user in a centered prompt.
 *
 * This function displays the specified prompt in a centered format using [printCentered] and
 * reads an integer input from the user. If the user enters a non-integer value or provides no
 * input, the default value of 0 is returned.
 *
 * @param prompt The message to be displayed as the prompt.
 * @return The integer entered by the user or 0 if the input is not a valid integer.
 */
fun readNextIntCentered(prompt: String): Int {
    printCentered(prompt)
    val input = readLine()?.toIntOrNull() ?: 0
    printCentered("> $input")
    return input
}

/**
 * Add staff will add a staff entry to the staff data class
 *
 */
fun addStaff(){
    val staffName = readNextLineCentered("Enter Staff name: ")
    val staffId = readNextIntCentered("Enter staff ID: ")
    val staffAddress = readNextLineCentered("Enter the staff address: ")
    val staffPhone = readNextIntCentered("Enter the staff members phone number: ")
    val staffType = readValidCategory("Type of staff: Teacher for teacher & Other for other: ")

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

/**
 * Add student will add a student entry to the student data class
 *
 */
fun addStudent(){
    val studentName = readNextLineCentered("Enter the full name of the student: ")
    val studentId = readNextIntCentered("Enter the students ID: ")
    val studentYear = readNextIntWithValidation("Enter the year the student is in: ", 1, 6)
    val studentClass = readNextIntWithValidation("Enter the class the student is in: ", 1, 4)
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

/**
 * Add grades will add a grade entry to the grade data class and the grade variable in the student data class
 *
 */
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

/**
 * Add teacher add a teacher entry to the teacher data class and to the teacher variable in the staff data class
 * it also checks a variable in the staff data class of typeOfWork and deppending on the answer to the variable
 * it will allow the teacher add or not.
 */
fun addTeacher(){
    val teacherId = readNextIntCentered("Enter the ID of the teacher: ")
    val subjectsTeaching = readNextLineCentered("Enter the subjects this teacher teaches: ")
    val classroomNumber = readNextIntCentered("Enter their classroom number: ")
    val classesAssigned = readNextIntCentered("Enter the classes you are assigned: ")
    val yearsWithTheSchool = readNextIntCentered("Enter the number of years with the school: ")
    val title = readNextLineCentered("Enter the teachers official job title: ")
    val childSafety = readNextLineCentered("Enter if this teacher is a child safety officer: ")

    val staff = schoolAPI.findStaff(teacherId)

    if (staff != null && staff.typeOfStaff.equals("Teacher", ignoreCase = true)) {
        val isAdded: Boolean = schoolAPI.addTeacher(
            Teacher(teacherId, subjectsTeaching, classroomNumber, classesAssigned, yearsWithTheSchool, title, childSafety)
        )
        if (isAdded) {
            schoolAPI.isAddTeacherUsed = true
            println("Teacher added successfully")
        } else {
            println("Failed to add Teacher details")
        }
    } else {
        println("Invalid staff ID or staff type is not Teacher")
    }
}

/**
 * List staff will list the staff entries in the staff data class
 *
 */
fun listStaff(){ println(schoolAPI.listAllStaff()) }

/**
 * List student will list the student entries in the student data class
 *
 */
fun listStudent(){ println(schoolAPI.listAllStudents()) }

/**
 * List grade will list the grade entries in the grade data class
 *
 */
fun listGrade(){ println(schoolAPI.listAllGrades()) }

/**
 * List teacher will list the teacher entries in the teacher data class
 *
 */
fun listTeacher(){ print(schoolAPI.listAllTeachers()) }

/**
 * Count staff will count the number of entries in the staff data class
 *
 */
fun countStaff(){ println(schoolAPI.countAllStaff()) }

/**
 * Count student will count the number of entries in the student data class
 *
 */
fun countStudent(){ println(schoolAPI.countAllStudent()) }

/**
 * Count grade will count the number of entries in the grade data class
 *
 */
fun countGrade(){ println(schoolAPI.countAllGrade()) }

/**
 * Count teacher will count the number of entries in the teacher data class
 *
 */
fun countTeacher(){ println(schoolAPI.countAllTeacher()) }

/**
 * Update staff will allow the user to enter a staff id to then update the variable of the staff data class associated
 * with that id entered
 */
fun updateStaff(){
    listStaff()
    if (schoolAPI.countAllStaff() > 0){
        val staffToUpdate = readNextInt("Enter the ID of the staff you wish to update: ")
        if (schoolAPI.isValidStaffId(staffToUpdate)){
            val staffName = readNextLineCentered("Enter Staff name: ")
            val staffId = readNextIntCentered("Enter staff ID: ")
            val staffAddress = readNextLineCentered("Enter the staff address: ")
            val staffPhone = readNextIntCentered("Enter the staff members phone number: ")
            val staffType = readValidCategory("Type of staff: 1 for teacher & 0 for other: ")

            if (schoolAPI.updateStaff(staffToUpdate, Staff(staffName, staffId, staffAddress, staffPhone, staffType))){
                val successMessage = "Staff updated successfully"
                printCentered(successMessage)
            } else{
                val errorMessage = "Failed to update staff details"
                printCentered(errorMessage)
            }
        } } }

/**
 * Update student will allow the user to enter a student id to then update the variable of the student data class associated
 *  * with that id entered
 */
fun updateStudent(){
    listStudent()
    if (schoolAPI.countAllStudent() > 0){
        val studentToUpdate = readNextInt("Enter the ID of the student you want to update: ")

        if (schoolAPI.isValidStudentId(studentToUpdate)){
            val studentName = readNextLineCentered("Enter the full name of the student: ")
            val studentId = readNextIntCentered("Enter the students ID: ")
            val studentYear = readNextIntWithValidation("Enter the year the student is in: ", 1, 6)
            val studentClass = readNextIntWithValidation("Enter the class the student is in: ", 1, 4)
            val studentAddress = readNextLineCentered("Enter the address of the student: ")
            val studentRecord = readNextLineCentered("Enter the students records: ")

            if (schoolAPI.updateStudent(studentToUpdate, Student(studentName, studentId, studentYear, studentClass,
                                        studentAddress, studentRecord))){
                println("Student updated successfully")
            } else{
                println("Failed to update student details")
            }
        } } }

/**
 * Update grade will allow the user to enter a student id to then update the variable of the grade data class associated
 *  * with that id entered
 */
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

/**
 * Update teacher will allow the user to enter a staff id to then update the variable of the teacher data class associated
 *  * with that id entered
 */
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

            val staff = schoolAPI.findStaff(teacherId)

            if (staff != null && staff.typeOfStaff.equals("Teacher", ignoreCase = true)) {
                if (schoolAPI.updateTeacher(teacherToUpdate, Teacher(teacherId, subjectsTeaching, classroomNumber,
                        classesAssigned,yearsWithTheSchool, title, childSafety))){
                    println("Teacher updated successfully")
                } else{
                    println("Failed to update Teacher details")
                }
            } else {
                println("Invalid staff ID or staff type is not Teacher")
            }
        } } }

/**
 * Delete staff will allow the user to enter a staff id and then delete that entry
 *
 */
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

/**
 * Delete student will allow the user to enter a student id and then delete that entry
 *
 */
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

/**
 * Delete grade will allow the user to enter a student id and then delete that entry
 *
 */
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

/**
 * Delete teacher will allow the user to enter a staff id and then delete that entry
 *
 */
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

/**
 * Displays the timetable for a student based on their ID.
 *
 * This function prompts the user to enter their student ID. It then retrieves the student's
 * information using [schoolAPI.findStudent] and generates a timetable using
 * [schoolAPI.generateTimetable]. The resulting timetable is printed in a centered format using
 * [printCentered]. If the entered student ID is invalid, an error message is returned.
 *
 * @return A formatted timetable string if the student is found, or an error message if the student
 * ID is invalid.
 */
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

/**
 * Generates and displays a report for a student based on the provided student ID.
 *
 * This function prompts the user to enter their student ID, retrieves the corresponding
 * student and grade information, generates a report, and displays the result in a centered format.
 *
 * @return Either the centered report for the student or an error message if an invalid ID is entered.
 */
fun report(): Any {
    val studentId = readNextIntCentered("Enter your student ID to view your report: ")
    val student = schoolAPI.findStudent(studentId)
    val grade = schoolAPI.findGrade(studentId)

    return if (student != null){
        val reportResult = schoolAPI.generateReport(student, grade)
        return printCentered(reportResult)
    } else {
        "Invalid ID entered"
    }
}


/**
 * Save allows the user to save the entries into the data classes they have added
 *
 */
fun save() {
    try {
        schoolAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Load allows the user to then load these entries next time they run the app
 *
 */
fun load() {
    try {
        schoolAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Exit app allows the user to exit the app
 *
 */
fun exitApp(){
    logger.info { "Exiting APP..." }
    System.exit(0)
}