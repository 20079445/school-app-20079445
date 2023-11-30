package persistence

import java.io.File
import kotlin.Throws
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import model.Grade
import model.Staff
import model.Student
import model.Teacher
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

class XMLSerializer(private val staffFile: File, private val studentFile: File) : Serializer<Any?> {

    @Throws(Exception::class)
    override fun readStaff(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Student::class.java))
        xStream.allowTypes(arrayOf(Staff::class.java))
        xStream.allowTypes(arrayOf(Grade::class.java))
        xStream.allowTypes(arrayOf(Teacher::class.java))
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
}