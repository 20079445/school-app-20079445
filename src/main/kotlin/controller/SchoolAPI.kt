package controller

import model.Grade
import model.Staff
import model.Student
import model.Teacher
import mu.KotlinLogging
import persistence.JSONSerializer

private val logger = KotlinLogging.logger {}

/**
 * The `SchoolAPI` class represents an API for managing school-related data, including students, staff, grades, and teachers.
 *
 * @param serializer The serializer used for data serialization and deserialization.
 */
class SchoolAPI(serializer: JSONSerializer) {

    private var students = ArrayList<Student>()
    private var staffs = ArrayList<Staff>()
    private var grades = ArrayList<Grade>()
    private var teachers = ArrayList<Teacher>()

    //private var studentSerializer: XMLSerializer = serializer
    //private var staffSerializer: XMLSerializer = serializer
    //private var gradeSerializer: XMLSerializer = serializer
    //private var teacherSerializer: XMLSerializer = serializer

    private var studentSerializer: JSONSerializer = serializer
    private var staffSerializer: JSONSerializer = serializer
    private var gradeSerializer: JSONSerializer = serializer
    private var teacherSerializer: JSONSerializer = serializer

    var isAddStudentUsed = false
    var isAddStaffUsed = false
    var isAddGradeUsed = false
    var isAddTeacherUsed = false

    /**
     * Adds an item to the specified [MutableList].
     *
     * @param item The item to be added to the list.
     * @param list The [MutableList] to which the item will be added.
     * @return `true` if the item is successfully added, `false` otherwise.
     * @param T The type of elements in the list.
     */
    fun <T> add(item: T, list: MutableList<T>): Boolean {
        return list.add(item) }

    /**
     * Adds a [Student] to the list of students.
     *
     * @param student The [Student] object to be added.
     * @return `true` if the student is successfully added, `false` otherwise.
     */
    fun addStudent(student: Student): Boolean {
        isAddStudentUsed = true
        return add(student, students) }

    /**
     * Adds a [Staff] to the list of staffs.
     *
     * @param staff The [Staff] object to be added.
     * @return `true` if the staff is successfully added, `false` otherwise.
     */
    fun addStaff(staff: Staff): Boolean {
        isAddStaffUsed = true
        return add(staff, staffs) }

    /**
     * Adds a [Grade] to the list of grades.
     *
     * @param grade The [Grade] object to be added.
     * @return `true` if the grade is successfully added, `false` otherwise.
     */
    fun addGrade(grade: Grade): Boolean {
        val student = students.find { it.studentId == grade.student }
        return if (student != null) {
            student.grades.add(grade)
            add(grade, grades)
            isAddGradeUsed = true
            true
        } else {
            false
        } }

    /**
     * Adds a [Teacher] to the list of teachers.
     *
     * @param teacher The [Teacher] object to be added.
     * @return `true` if the teacher is successfully added, `false` otherwise.
     */
    fun addTeacher(teacher: Teacher): Boolean {
        val staff = staffs.find { it.staffId == teacher.staff }
        return if (staff != null){
            staff.teachers.add(teacher)
            add(teacher, teachers)
            isAddTeacherUsed = true
            true
        } else {
            false
        } }

    /**
     * Generic function to list all entries of a specific type.
     *
     * @param entries List of entries to be listed.
     * @param name The name representing the type of entries.
     * @return A formatted string listing all entries or indicating none are present.
     */
    private inline fun <reified T> listAll(entries: List<T>, name: String): String {
        return if (entries.isEmpty()) "No $name entries"
        else entries.joinToString(separator = "\n") { item ->
            entries.indexOf(item).toString() + ": " + item.toString()
        }
    }

    /**
     * List all students.
     *
     * @return A formatted string listing all students or indicating none are present.
     */
    fun listAllStudents(): String = listAll(students, "Student")

    /**
     * List all staff members.
     *
     * @return A formatted string listing all staff members or indicating none are present.
     */
    fun listAllStaff(): String = listAll(staffs, "Staff")

    /**
     * List all grades.
     *
     * @return A formatted string listing all grades or indicating none are present.
     */
    fun listAllGrades(): String {
        val gradeList = grades.joinToString("\n") { grade ->
            "Grade for Student ID ${grade.student}: English=${grade.english}, Maths=${grade.maths}, Geography=${grade.geography}, History=${grade.history}, Civics=${grade.civics}, Irish=${grade.irish}" }
        return if (gradeList.isNotEmpty()) {
            gradeList } else {
            "No Grade entries" } }

    /**
     * List all teachers.
     *
     * @return A formatted string listing all teachers or indicating none are present.
     */
    fun listAllTeachers(): String = listAll(teachers, "Teacher")


    /**
     * Generic function to count the number of items in a list.
     *
     * @param list The list of items to count.
     * @return The total number of items in the list.
     */
    fun <T> countAll(list: List<T>): Int {
        return list.size
    }

    /**
     * Count all staff members.
     *
     * @return The total number of staff members.
     */
    fun countAllStaff(): Int = countAll(staffs)

    /**
     * Count all students.
     *
     * @return The total number of students.
     */
    fun countAllStudent(): Int = countAll(students)

    /**
     * Count all grades.
     *
     * @return The total number of grades.
     */
    fun countAllGrade(): Int = countAll(grades)

    /**
     * Count all teachers.
     *
     * @return The total number of teachers.
     */
    fun countAllTeacher(): Int = countAll(teachers)

    /**
     * Update staff details.
     *
     * @param staffToUpdate The ID of the staff member to update.
     * @param staff The new staff details.
     * @return `true` if the staff member was successfully updated, `false` otherwise.
     */
    fun updateStaff(staffToUpdate: Int, staff: Staff): Boolean{
        val foundStaff = findStaff(staffToUpdate)

        if (foundStaff != null){
            foundStaff.name = staff.name
            foundStaff.staffId = staff.staffId
            foundStaff.staffAddress = staff.staffAddress
            foundStaff.staffPhone = staff.staffPhone
            foundStaff.typeOfStaff = staff.typeOfStaff
            foundStaff.teachers = staff.teachers
            return true
        }else {
            return false
        }
    }

    /**
     * Update student details.
     *
     * @param studentToUpdate The ID of the student to update.
     * @param student The new student details.
     * @return `true` if the student was successfully updated, `false` otherwise.
     */
    fun updateStudent(studentToUpdate: Int, student: Student): Boolean{
        val foundStudent = findStudent(studentToUpdate)

        if (foundStudent != null){
            foundStudent.name = student.name
            foundStudent.studentId = student.studentId
            foundStudent.year = student.year
            foundStudent.address = student.address
            foundStudent.record = student.record
            foundStudent.grades = student.grades
            return true
        } else{
            return false
        }
    }

    /**
     * Update grade details.
     *
     * @param gradeToUpdate The ID of the grade to update.
     * @param grade The new grade details.
     * @return `true` if the grade was successfully updated, `false` otherwise.
     */
    fun updateGrade(gradeToUpdate: Int, grade: Grade): Boolean{
        val foundGrade = findGrade(gradeToUpdate)

        if (foundGrade != null){
            foundGrade.student = grade.student
            foundGrade.english = grade.english
            foundGrade.maths = grade.maths
            foundGrade.geography = grade.geography
            foundGrade.history = grade.history
            foundGrade.civics = grade.civics
            foundGrade.irish = grade.irish
            return true
        } else {
            return false
        }
    }

    /**
     * Update teacher details.
     *
     * @param teacherToUpdate The ID of the teacher to update.
     * @param teacher The new teacher details.
     * @return `true` if the teacher was successfully updated, `false` otherwise.
     */
    fun updateTeacher(teacherToUpdate: Int, teacher: Teacher): Boolean{
        val foundTeacher = findTeacher(teacherToUpdate)

        if (foundTeacher != null){
            foundTeacher.staff = teacher.staff
            foundTeacher.subjects = teacher.subjects
            foundTeacher.classroom = teacher.classroom
            foundTeacher.yearsWithSchool = teacher.yearsWithSchool
            foundTeacher.jobTitle = teacher.jobTitle
            foundTeacher.childSafety = teacher.childSafety
            return true
        } else {
            return false
        }
    }

    /**
     * Delete a staff member.
     *
     * @param staffIdToDelete The ID of the staff member to delete.
     * @return The deleted staff member, or `null` if not found.
     */
    fun deleteStaff(staffIdToDelete: Int): Staff? {
        val staffToDelete = staffs.find { it.staffId == staffIdToDelete }
        return if (staffToDelete != null) {
            staffs.remove(staffToDelete)
            staffToDelete
        } else {
            return null
        }
    }

    /**
     * Delete a student.
     *
     * @param studentToDelete The ID of the student to delete.
     * @return The deleted student, or `null` if not found.
     */
    fun deleteStudent(studentToDelete: Int): Student?{
        val studentToDelete = students.find { it.studentId == studentToDelete }
        return if (studentToDelete != null){
            students.remove(studentToDelete)
            studentToDelete
        } else {
            return null
        }
    }

    /**
     * Delete a grade.
     *
     * @param gradeToDelete The ID of the grade to delete.
     * @return The deleted grade, or a message if not found.
     */
    fun deleteGrade(gradeToDelete: Int): Any{
        val gradeToDelete = grades.find { it.student == gradeToDelete }
        return if (gradeToDelete != null){
            grades.remove(gradeToDelete)
            gradeToDelete
        } else {
            return "No Grade entry with this ID try again"
        }
    }

    /**
     * Delete a teacher.
     *
     * @param teacherToDelete The ID of the teacher to delete.
     * @return The deleted teacher, or a message if not found.
     */
    fun deleteTeacher(teacherToDelete: Int): Any{
        val teacherToDelete = teachers.find { it.staff == teacherToDelete }
        return if (teacherToDelete != null){
            teachers.remove(teacherToDelete)
            teacherToDelete
        } else {
            return "No Teacher entry with this ID try again"
        }
    }

    /**
     * Check if a staff ID is valid.
     *
     * @param staffId The staff ID to check.
     * @return `true` if the staff ID is valid, `false` otherwise.
     */
    fun isValidStaffId(staffId: Int): Boolean {
        return staffs.any { it.staffId == staffId } }

    /**
     * Check if a student ID is valid.
     *
     * @param studentId The student ID to check.
     * @return `true` if the student ID is valid, `false` otherwise.
     */
    fun isValidStudentId(studentId: Int): Boolean {
        return students.any { it.studentId == studentId } }

    /**
     * Check if a grade ID is valid.
     *
     * @param gradeId The grade ID to check.
     * @return `true` if the grade ID is valid, `false` otherwise.
     */
    fun isValidGradeId(gradeId: Int): Boolean {
        return grades.any { it.student == gradeId } }

    /**
     * Check if a teacher ID is valid.
     *
     * @param teacherId The teacher ID to check.
     * @return `true` if the teacher ID is valid, `false` otherwise.
     */
    fun isValidTeacherId(teacherId: Int): Boolean {
        return teachers.any { it.staff == teacherId } }

    /**
     * Find staff by staff ID.
     *
     * @param staffId The staff ID to search for.
     * @return The found staff or `null` if not found.
     */
    fun findStaff(staffId: Int): Staff? {
        return staffs.find { it.staffId == staffId } }

    /**
     * Find student by student ID.
     *
     * @param studentId The student ID to search for.
     * @return The found student or `null` if not found.
     */
    fun findStudent(studentId: Int): Student? {
        return students.find { it.studentId == studentId } }

    /**
     * Find grade by student ID.
     *
     * @param studentId The student ID to search for the corresponding grade.
     * @return The found grade or `null` if not found.
     */
    fun findGrade(studentId: Int): Grade? {
        return grades.find { it.student == studentId } }

    /**
     * Find teacher by teacher ID.
     *
     * @param teacherId The teacher ID to search for.
     * @return The found teacher or `null` if not found.
     */
    fun findTeacher(teacherId: Int): Teacher? {
        return teachers.find { it.staff == teacherId } }

    /**
     * Generates a timetable for a given student with randomly assigned subjects and teachers.
     *
     * @param student The student for whom the timetable is generated.
     * @param staffInfo Information about staff, including names of teachers.
     * @return The generated timetable as a formatted string.
     */
    fun generateTimetable(student: Student, staffInfo: String): String {
        val timetable = StringBuilder()
        val name = student.name

        timetable.appendLine("----------------------------------------------------------------------")
        timetable.appendLine("|   TimeTable:       $name                                           |")
        timetable.appendLine("|--------------------------------------------------------------------|")
        timetable.appendLine("|    Time     |      Subject          |             Teacher          |")
        timetable.appendLine("|--------------------------------------------------------------------|")

        val timeSlots = listOf("9:15", "10:15", "11:15", "12:15", "13:15", "14:15", "15:15")

        // line of code i had to get online
        val staffNames = Regex("name=([^,]+)").findAll(staffInfo).map { it.groupValues[1] }.toList()

        for (timeSlot in timeSlots) {
            val randomSubject = getRandomSubject()
            val randomTeacher = staffNames.random()

            timetable.appendLine("|    $timeSlot    |   ${randomSubject.padEnd(23)} |   ${randomTeacher.padEnd(28)} |")
            timetable.appendLine("|--------------------------------------------------------------------|")
        }

        return timetable.toString().trimIndent()
    }

    /**
     * Gets a randomly selected subject from the list of predefined subjects.
     *
     * @return A randomly selected subject.
     */
    fun getRandomSubject(): String {
        val subjects = listOf("English", "Maths", "Geography", "History", "Civics", "Irish")
        return subjects.random()
    }

    /**
     * Generates a report card for a student based on the provided grades.
     *
     * @param student The student for whom the report card is generated.
     * @param grade The grade information for the student.
     * @return A formatted report card as a string.
     */
    fun generateReport(student: Student, grade: Grade?): String {
        val report = StringBuilder()
        val name = student.name

        report.appendLine("---------------------------------------------------")
        report.appendLine("|   Report card for $name                         |")
        report.appendLine("|-------------------------------------------------|")
        report.appendLine("|     Subject          |           Grade          |")
        report.appendLine("|-------------------------------------------------|")

        val subjects = listOf("English", "Maths", "Geography", "History", "Civics", "Irish")

        for (subject in subjects) {
            val subjectGrade = when (subject) {
                "English" -> grade?.english.toString()
                "Maths" -> grade?.maths.toString()
                "Geography" -> grade?.geography.toString()
                "History" -> grade?.history.toString()
                "Civics" -> grade?.civics.toString()
                "Irish" -> grade?.irish.toString()
                else -> "-"
            }

            report.appendLine("|    ${subject.padEnd(20)} |         ${subjectGrade.padEnd(21)} |")
            report.appendLine("|-------------------------------------------------|")
        }

        return report.toString().trimIndent()
    }

    /**
     * Loads data from the specified serializers into the corresponding lists.
     * Displays loaded data information for each category (students, staff, grades, teachers).
     *
     * @throws Exception if an error occurs during the loading process.
     */
    @Throws(Exception::class)
    fun load() {
        logger.info("Loading data...")
            students = studentSerializer.readStudent() as ArrayList<Student>
            println("Loaded Students:\n${listAllStudents()}")
            staffs = staffSerializer.readStaff() as ArrayList<Staff>
            println("Loaded Staff:\n${listAllStaff()}")
            grades = gradeSerializer.readGrade() as ArrayList<Grade>
            println("Loaded Grade:\\n${listAllGrades()}\" ")
            teachers = teacherSerializer.readTeacher() as ArrayList<Teacher>
            println("Loaded Teacher:\\n${listAllTeachers()}\" ")
        logger.info("Data loaded successfully.")
    }

    /**
     * Stores data from the lists into the specified serializers.
     * Displays a warning if any of the lists is empty and skips storing that category.
     *
     * @throws Exception if an error occurs during the storing process.
     */
    @Throws(Exception::class)
    fun store() {
        logger.info("Storing data...")
        if (students != null) {
            studentSerializer.writeStudent(students)
        } else {
            logger.warn("Students list is empty. Skipping storing student.") }
        if (staffs != null) {
            staffSerializer.writeStaff(staffs)
        } else {
            logger.warn("Staffs list is empty. Skipping storing Staff.") }
        if (grades != null) {
            gradeSerializer.writeGrade(grades)
        } else {
            logger.warn("Grades list is empty. Skipping storing Grade.") }
        if (teachers != null) {
            teacherSerializer.writeTeacher(teachers)
        } else {
            logger.warn("Teachers list is empty. Skipping storing Teacher.") }
        logger.info("Data stored successfully.")
    }
}

