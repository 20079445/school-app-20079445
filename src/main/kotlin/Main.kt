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
         > |   20) Save Entries             |
         > |   21) Load Entries             |
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
            2 -> list()
            3 -> count()
            20 -> save()
            21 -> load()
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

fun list(){
    if (1 > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                 > |   1)   List Staff details     |
                 > |   2)   List Student details   |
                 > |   3)   List student grades    |
                 > |   4)   List Teacher details   |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

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

fun count(){
    if (1 > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                 > |   1)   Count Staff details    |
                 > |   2)   Count Student details  |
                 > |   3)   Count student grades   |
                 > |   4)   Count Teacher details  |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

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

fun listStaff(){
    println(schoolAPI.listAllStaff())
}

fun listStudent(){
    println(schoolAPI.listAllStudents())
}

fun listGrade(){
    println(schoolAPI.listAllGrades())
}

fun listTeacher(){
    print(schoolAPI.listAllTeachers())
}

fun countStaff(){
    println(schoolAPI.countAllStaff())
}

fun countStudent(){
    println(schoolAPI.countAllStudent())
}

fun countGrade(){
    println(schoolAPI.countAllGrade())
}

fun countTeacher(){
    println(schoolAPI.countAllTeacher())
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