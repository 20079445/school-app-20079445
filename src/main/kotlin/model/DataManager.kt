package model

import persistence.Serializer

class DataManager(private val studentSerializer: Serializer<List<Student>>,
                  private val staffSerializer: Serializer<List<Staff>>,
                  private val gradeSerializer: Serializer<List<Grade>>,
                  private val teacherSerializer: Serializer<List<Teacher>>) {

    fun loadStudents(): Any? {
        return studentSerializer.read()
    }

    fun loadStaff(): Any? {
        return staffSerializer.read()
    }

    fun loadGrade(): Any? {
        return gradeSerializer.read()
    }

    fun loadTeacher(): Any? {
        return teacherSerializer.read()
    }

    fun storeStudents(students: List<Student>) {
        studentSerializer.write(students)
    }

    fun storeStaff(staffs: List<Staff>) {
        staffSerializer.write(staffs)
    }

    fun storeGrade(grades: List<Staff>) {
        staffSerializer.write(grades)
    }

    fun storeTeacher(teachers: List<Staff>) {
        staffSerializer.write(teachers)
    }
}

