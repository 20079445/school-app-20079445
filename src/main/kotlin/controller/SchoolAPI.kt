package controller

import model.Staff
import model.Student
import persistence.Serializer

class SchoolAPI(serializerType: Serializer) {
    private var students = ArrayList<Student>()
    private var staffs = ArrayList<Staff>()
    private var serializer: Serializer = serializerType


    fun addStudent(student: Student): Boolean {
        return students.add(student)
    }

    fun addStaff(staff: Staff): Boolean{
        return staffs.add(staff)
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

