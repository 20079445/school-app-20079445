
import controller.SchoolAPI
import model.Staff
import model.Student
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File


private val logger = KotlinLogging.logger {}
private val schoolAPI = SchoolAPI(XMLSerializer(File("notes.xml")))

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return readNextInt(
        """ 
         > ----------------------------------
         > |      SCHOOL DATABASE APP       |
         > ----------------------------------
         > | SCHOOL MENU                    |
         > |   1)   Add An Entry            |
         > |   2)   List Entry              |
         > |   3)   Count Entry             |
         > |   4)                           |
         > |   5)                           |
         > ----------------------------------
         > |   20) Save notes               |
         > |   21) Load notes               |
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> add()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun add(){
    if (1 > 0) {
    val option = readNextInt(
        """
                  > --------------------------------
                 > |   1)   Add Staff details       |
                 > |   2)   Add Student details     |
                 > |   3)   Add student grades      |
                 > |   4)   Add Teacher details     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

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

fun addStaff(){
    val staffName = readNextLine("Enter Staff name: ")
    val staffId = readNextInt("Enter staff ID: ")
    val staffAddress = readNextLine("Enter the staff address: ")
    val staffPhone = readNextInt("Enter the staff members phone number: ")
    val staffType = readNextInt("Type of staff: 1 for teacher & 0 for other: ")

    val isAdded : Boolean = schoolAPI.addStaff(Staff(staffName, staffId, staffAddress,
                                                     staffPhone, staffType)
    )
    if (isAdded){
        println("Staff added successfully")
    } else{
        println("Failed to add student details")
    }
}

fun addStudent(){
    val studentName = readNextLine("Enter the full name of the student: ")
    val studentId = readNextInt("Enter the students ID: ")
    val studentYear = readNextInt("Enter the year the student is in: ")
    val studentAddress = readNextLine("Enter the address of the student: ")
    val studentRecord = readNextLine("Enter the students records: ")

    val isAdded : Boolean = schoolAPI.addStudent(Student(studentName, studentId, studentYear,
                                        studentAddress, studentRecord)
    )
    if (isAdded){
        println("Student added successfully")
    } else{
        println("Failed to add student details")
    }
}

fun addGrades(){

}

fun addTeacher(){

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