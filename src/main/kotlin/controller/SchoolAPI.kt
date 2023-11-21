package controller

import model.Staff
import model.Student
import persistence.Serializer

class SchoolAPI(serializerType: Serializer) {
    private var students = ArrayList<Student>()
    private var staffs = ArrayList<Staff>()
    private var serializer: Serializer = serializerType

    private fun formatListStudentString(notesToFormat: List<Student>): String =
        notesToFormat.joinToString(separator = "\n") { student ->
            students.indexOf(student).toString() + ": " + student.toString()
        }

    private fun formatListStaffString(notesToFormat: List<Staff>): String =
        notesToFormat.joinToString(separator = "\n") { staff ->
            staffs.indexOf(staff).toString() + ": " + staff.toString()
        }


    fun addStudent(student: Student): Boolean {
        return students.add(student)
    }

    fun addStaff(staff: Staff): Boolean{
        return staffs.add(staff)
    }

    fun listAllStudents(): String{
        return if (students.isEmpty()) "No Student entries"
        else formatListStudentString(students)
    }

    fun listAllStaff(): String{
        return if (staffs.isEmpty()) "no Staff entries"
        else formatListStaffString(staffs)
    }

    @Throws(Exception::class)
    fun load() {
        students = serializer.read() as ArrayList<Student>
    }


    @Throws(Exception::class)
    fun store() {
        serializer.write(students)
    }
}

