package controller

import model.Grade
import model.Staff
import model.Student
import model.Teacher
import persistence.Serializer

class SchoolAPI(serializerType: Serializer<Any?>) {
    private var students = ArrayList<Student>()
    private var staffs = ArrayList<Staff>()
    private var grades = ArrayList<Grade>()
    private var teachers = ArrayList<Teacher>()
    private var serializer: Serializer<Any?> = serializerType

    private fun formatListStudentString(entryToFormat: List<Student>): String =
        entryToFormat.joinToString(separator = "\n") { student ->
            students.indexOf(student).toString() + ": " + student.toString()
        }

    private fun formatListStaffString(entryToFormat: List<Staff>): String =
        entryToFormat.joinToString(separator = "\n") { staff ->
            staffs.indexOf(staff).toString() + ": " + staff.toString()
        }

    private fun formatListGradeString(entryToFormat: List<Grade>): String =
        entryToFormat.joinToString(separator = "\n") { grade ->
            grades.indexOf(grade).toString() + ": " + grade.toString()
        }

    private fun formatListTeacherString(entryToFormat: List<Teacher>): String =
        entryToFormat.joinToString(separator = "\n") { teacher ->
            teachers.indexOf(teacher).toString() + ": " + teacher.toString()
        }


    fun addStudent(student: Student): Boolean {
        return students.add(student)
    }

    fun addStaff(staff: Staff): Boolean{
        return staffs.add(staff)
    }

    fun addGrade(grade: Grade): Boolean{
        return grades.add(grade)
    }

    fun addTeacher(teacher: Teacher): Boolean{
        return teachers.add(teacher)
    }

    fun listAllStudents(): String{
        return if (students.isEmpty()) "No Student entries"
        else formatListStudentString(students)
    }

    fun listAllStaff(): String{
        return if (staffs.isEmpty()) "no Staff entries"
        else formatListStaffString(staffs)
    }

    fun listAllGrades():String{
        return if (grades.isEmpty()) "no Grade entries"
        else formatListGradeString(grades)
    }

    fun listAllTeachers(): String{
        return if (teachers.isEmpty()) "no Teacher entries"
        else formatListTeacherString(teachers)
    }

    fun countAllStaff(): Int{
        return staffs.size
    }

    fun countAllStudent(): Int{
        return students.size
    }

    fun countAllGrade(): Int {
        return grades.size
    }

    fun countAllTeacher(): Int{
        return teachers.size
    }

    @Throws(Exception::class)
    fun <T> load(Class: Class<T>) {
        val data = serializer.read() as List<T>
        students = ArrayList(data)
    }

    @Throws(Exception::class)
    fun <T> store(data: List<T>) {
        serializer.write(data)
    }
}

