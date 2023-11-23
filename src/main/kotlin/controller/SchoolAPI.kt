package controller

import model.Grade
import model.Staff
import model.Student
import model.Teacher
import persistence.Serializer
import persistence.XMLSerializer

class SchoolAPI(serializerType: XMLSerializer) {
    private var students = ArrayList<Student>()
    private var staffs = ArrayList<Staff>()
    private var grades = ArrayList<Grade>()
    private var teachers = ArrayList<Teacher>()

    private var serializer: XMLSerializer = serializerType

    fun <T> add(item: T, list: MutableList<T>): Boolean {
        return list.add(item)
    }
    fun addStudent(student: Student): Boolean = add(student, students)
    fun addStaff(staff: Staff): Boolean = add(staff, staffs)
    fun addGrade(grade: Grade): Boolean = add(grade, grades)
    fun addTeacher(teacher: Teacher): Boolean = add(teacher, teachers)

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
        students = serializer.read() as ArrayList<Student>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(students)
    }
}

