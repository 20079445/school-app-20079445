package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import model.Grade
import model.Staff
import model.Student
import model.Teacher
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

/**
 * Binary serializer
 *
 * @property staffFile
 * @property studentFile
 * @property gradeFile
 * @property teacherFile
 * @constructor Create empty Binary serializer
 */
class BinarySerializer(private val staffFile: File, private val studentFile: File, private val gradeFile: File, private val teacherFile: File) : Serializer<Any?> {

    @Throws(Exception::class)
    override fun readStaff(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Staff::class.java))
        val inputStaffStream = xStream.createObjectInputStream(FileReader(staffFile))
        val obj = inputStaffStream.readObject() as Any
        inputStaffStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun readStudent(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Student::class.java))
        val inputStudentStream = xStream.createObjectInputStream(FileReader(studentFile))
        val obj = inputStudentStream.readObject() as Any
        inputStudentStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun readGrade(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Grade::class.java))
        val inputGradeStream = xStream.createObjectInputStream(FileReader(gradeFile))
        val obj = inputGradeStream.readObject() as Any
        inputGradeStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun readTeacher(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Teacher::class.java))
        val inputTeacherStream = xStream.createObjectInputStream(FileReader(teacherFile))
        val obj = inputTeacherStream.readObject() as Any
        inputTeacherStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun writeStaff(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(staffFile))
        outputStream.writeObject(obj)
        outputStream.close()
    }

    @Throws(Exception::class)
    override fun writeStudent(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(studentFile))
        outputStream.writeObject(obj)
        outputStream.close()
    }

    @Throws(Exception::class)
    override fun writeGrade(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(gradeFile))
        outputStream.writeObject(obj)
        outputStream.close()
    }

    @Throws(Exception::class)
    override fun writeTeacher(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(teacherFile))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}