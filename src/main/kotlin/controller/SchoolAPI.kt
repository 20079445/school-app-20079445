package controller

import model.Grade
import model.Staff
import model.Student
import model.Teacher
import persistence.XMLSerializer
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class SchoolAPI(studentSerializerType: XMLSerializer,
                staffSerializerType: XMLSerializer,
                gradeSerializerType: XMLSerializer,
                teacherSerializerType: XMLSerializer) {

    private var students = ArrayList<Student>()
    private var staffs = ArrayList<Staff>()
    private var grades = ArrayList<Grade>()
    private var teachers = ArrayList<Teacher>()

    private var studentSerializer: XMLSerializer = studentSerializerType
    private var staffSerializer: XMLSerializer = staffSerializerType
    private var gradeSerializer: XMLSerializer = gradeSerializerType
    private var teacherSerializer: XMLSerializer = teacherSerializerType

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

    @Throws(Exception::class)
    fun load() {
        logger.info("Loading data...")
        val test = true
        when(test){
            isAddStudentUsed -> students = studentSerializer.read() as ArrayList<Student>
            isAddStaffUsed -> staffs = staffSerializer.read() as ArrayList<Staff>
            isAddGradeUsed -> grades = gradeSerializer.read() as ArrayList<Grade>
            isAddTeacherUsed -> teachers = teacherSerializer.read() as ArrayList<Teacher>
            else -> println("There is nothing to load")
        }
    }

    @Throws(Exception::class)
    fun store() {
        logger.info("Storing data...")
        studentSerializer.write(students)
        staffSerializer.write(staffs)
        gradeSerializer.write(grades)
        teacherSerializer.write(teachers)
        logger.info("Data stored successfully.")
    }
}

