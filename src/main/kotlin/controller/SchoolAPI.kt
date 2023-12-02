package controller

import model.Grade
import model.Staff
import model.Student
import model.Teacher
import persistence.XMLSerializer
import mu.KotlinLogging
import persistence.JSONSerializer

private val logger = KotlinLogging.logger {}

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

    fun <T> add(item: T, list: MutableList<T>): Boolean {
        return list.add(item) }
    fun addStudent(student: Student): Boolean {
        isAddStudentUsed = true
        return add(student, students) }
    fun addStaff(staff: Staff): Boolean {
        isAddStaffUsed = true
        return add(staff, staffs) }
    fun addGrade(grade: Grade): Boolean {
        val student = students.find { it.studentId == grade.student }
        return if (student != null) {
            student.grades.add(grade)
            isAddGradeUsed = true
            true
        } else {
            false
        } }
    fun addTeacher(teacher: Teacher): Boolean {
        val staff = staffs.find { it.staffId == teacher.staff }
        return if (staff != null){
            staff.teachers.add(teacher)
            isAddTeacherUsed = true
            true
        } else {
            false
        } }

    private inline fun <reified T> listAll(entries: List<T>, name: String): String {
        return if (entries.isEmpty()) "No $name entries"
        else entries.joinToString(separator = "\n") { item ->
            entries.indexOf(item).toString() + ": " + item.toString()
        }
    }
    fun listAllStudents(): String = listAll(students, "Student")
    fun listAllStaff(): String = listAll(staffs, "Staff")
    fun listAllGrades(): String = listAll(grades, "Grade")
    fun listAllTeachers(): String = listAll(teachers, "Teacher")


    fun <T> countAll(list: List<T>): Int {
        return list.size
    }
    fun countAllStaff(): Int = countAll(staffs)
    fun countAllStudent(): Int = countAll(students)
    fun countAllGrade(): Int = countAll(grades)
    fun countAllTeacher(): Int = countAll(teachers)

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

    fun deleteStaff(staffIdToDelete: Int): Staff? {
        val staffToDelete = staffs.find { it.staffId == staffIdToDelete }
        return if (staffToDelete != null) {
            staffs.remove(staffToDelete)
            staffToDelete
        } else {
            return null
        }
    }

    fun deleteStudent(studentToDelete: Int): Student?{
        val studentToDelete = students.find { it.studentId == studentToDelete }
        return if (studentToDelete != null){
            students.remove(studentToDelete)
            studentToDelete
        } else {
            return null
        }
    }

    fun deleteGrade(gradeToDelete: Int): Any{
        val gradeToDelete = grades.find { it.student == gradeToDelete }
        return if (gradeToDelete != null){
            grades.remove(gradeToDelete)
            gradeToDelete
        } else {
            return "No Grade entry with this ID try again"
        }
    }

    fun deleteTeacher(teacherToDelete: Int): Any{
        val teacherToDelete = teachers.find { it.staff == teacherToDelete }
        return if (teacherToDelete != null){
            teachers.remove(teacherToDelete)
            teacherToDelete
        } else {
            return "No Teacher entry with this ID try again"
        }
    }

    fun isValidStaffId(staffId: Int): Boolean {
        return staffs.any { it.staffId == staffId } }
    fun isValidStudentId(studentId: Int): Boolean {
        return students.any { it.studentId == studentId } }
    fun isValidGradeId(gradeId: Int): Boolean {
        return grades.any { it.student == gradeId } }
    fun isValidTeacherId(teacherId: Int): Boolean {
        return teachers.any { it.staff == teacherId } }

    fun findStaff(staffId: Int): Staff? {
        return staffs.find { it.staffId == staffId } }
    fun findStudent(studentId: Int): Student? {
        return students.find { it.studentId == studentId } }
    fun findGrade(studentId: Int): Grade? {
        return grades.find { it.student == studentId } }
    fun findTeacher(teacherId: Int): Teacher? {
        return teachers.find { it.staff == teacherId } }

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



    fun getRandomSubject(): String {
        val subjects = listOf("English", "Maths", "Geography", "History", "Civics", "Irish")
        return subjects.random()
    }

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
                "English" -> grade?.english?.toString() ?: "-"
                "Maths" -> grade?.maths?.toString() ?: "-"
                "Geography" -> grade?.geography?.toString() ?: "-"
                "History" -> grade?.history?.toString() ?: "-"
                "Civics" -> grade?.civics?.toString() ?: "-"
                "Irish" -> grade?.irish?.toString() ?: "-"
                else -> "-"
            }

            report.appendLine("|    ${subject.padEnd(20)} |         ${subjectGrade.padEnd(21)} |")
            report.appendLine("|-------------------------------------------------|")
        }

        return report.toString().trimIndent()
    }

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

