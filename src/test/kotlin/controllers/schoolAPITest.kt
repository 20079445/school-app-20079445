package controllers

import model.Staff
import model.Student
import controller.SchoolAPI
import model.Grade
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.JSONSerializer
import persistence.XMLSerializer
import schoolAPI
import java.io.File
import kotlin.test.assertEquals

class SchoolAPITest() {

    private var learnKotlin: Staff? = null
    private var summerHoliday: Staff? = null
    private var codeApp: Staff? = null
    private var testApp: Staff? = null
    private var swim: Staff? = null
    private var populatedStaff: SchoolAPI? = SchoolAPI(JSONSerializer(File("Staff.json"), File("Student.json"), File("Grade.json"), File("Teacher.json")))
    private var emptyStaff: SchoolAPI? = SchoolAPI(JSONSerializer(File("Staff.json"), File("Student.json"), File("Grade.json"), File("Teacher.json")))

    private var learnKotlin2: Student? = null
    private var summerHoliday2: Student? = null
    private var codeApp2: Student? = null
    private var testApp2: Student? = null
    private var swim2: Student? = null
    private var populatedStudent: SchoolAPI? = SchoolAPI(JSONSerializer(File("Staff.json"), File("Student.json"), File("Grade.json"), File("Teacher.json")))
    private var emptyStudent: SchoolAPI? = SchoolAPI(JSONSerializer(File("Staff.json"), File("Student.json"), File("Grade.json"), File("Teacher.json")))


    @BeforeEach
    fun setup() {
        learnKotlin = Staff("Learning Kotlin", 5, "College", 0, "Teacher")
        summerHoliday = Staff("Summer Holiday to France", 1, "Holiday", 0, "Other")
        codeApp = Staff("Code App", 4, "Work", 0, "Teacher")
        testApp = Staff("Test App", 2, "Work", 0, "Other")
        swim = Staff("Swim - Pool", 3, "Hobby", 0, "Teacher")

        learnKotlin2 = Student("name1", 1, 1, 1, "address1", "record1")
        summerHoliday2 = Student("name2", 2, 1, 1, "address2", "record2")
        codeApp2 = Student("name3", 3, 1, 1, "address3", "record3")
        testApp2 = Student("name4", 4, 1, 1, "address4", "record4")
        swim2 = Student("name5", 5, 1, 1, "address5", "record5")

        populatedStaff!!.addStaff(learnKotlin!!)
        populatedStaff!!.addStaff(summerHoliday!!)
        populatedStaff!!.addStaff(codeApp!!)
        populatedStaff!!.addStaff(testApp!!)
        populatedStaff!!.addStaff(swim!!)

        populatedStudent!!.addStudent(learnKotlin2!!)
        populatedStudent!!.addStudent(summerHoliday2!!)
        populatedStudent!!.addStudent(codeApp2!!)
        populatedStudent!!.addStudent(testApp2!!)
        populatedStudent!!.addStudent(swim2!!)
    }


    @AfterEach
    fun tearDown() {
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedStaff = null
        emptyStaff = null

        learnKotlin2 = null
        summerHoliday2 = null
        codeApp2 = null
        testApp2 = null
        swim2 = null
        populatedStudent = null
        emptyStudent = null

    }

    @Nested
    inner class Add {
        @Test
        fun `adding a staff entry to a populated list adds to ArrayList`() {
            val newStaff = Staff("Study Lambdas", 6, "College", 0, "Other")
            assertEquals(5, populatedStaff!!.countAllStaff())
            assertTrue(populatedStaff!!.addStaff(newStaff))
            assertEquals(6, populatedStaff!!.countAllStaff())
            assertEquals(newStaff, populatedStaff!!.findStaff(6))
        }

        @Test
        fun `adding a staff entry to an empty list`() {
            val newStaff = Staff("Study Lambdas", 1, "College", 0, "Other")
            assertEquals(0, emptyStaff!!.countAllStaff())
            assertTrue(emptyStaff!!.addStaff(newStaff))
            assertEquals(1, emptyStaff!!.countAllStaff())
            assertEquals(newStaff, emptyStaff!!.findStaff(1))
        }

        @Test
        fun `adding a student entry to a populated list adds to ArrayList`() {
            val newStudent = Student("name1", 6, 1, 0, "address1", "record1")
            assertEquals(0, populatedStudent!!.countAllStaff())
            assertTrue(populatedStudent!!.addStudent(newStudent))
            assertEquals(6, populatedStudent!!.countAllStudent())
            assertEquals(newStudent, populatedStudent!!.findStudent(6))
        }

        @Test
        fun `adding a student entry to an empty list`() {
            val newStudent = Student("name1", 1, 1, 0, "address1", "record1")
            assertEquals(0, emptyStudent!!.countAllStaff())
            assertTrue(emptyStudent!!.addStudent(newStudent))
            assertEquals(1, emptyStudent!!.countAllStudent())
            assertEquals(newStudent, emptyStudent!!.findStudent(1))
        }

    }

    @Nested
    inner class List {

        @Test
        fun `listAllStaff returns No staff entries Stored message when ArrayList is empty`() {
            assertEquals(0, emptyStaff!!.countAllStaff())
            assertTrue(emptyStaff!!.countAllStaff() == 0)
        }

        @Test
        fun `listAllStaff returns staff entries when ArrayList has entries stored`() {
            assertEquals(5, populatedStaff!!.countAllStaff())
            val notesString = populatedStaff!!.listAllStaff().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
        }

        @Test
        fun `listAllStudents returns No student entries Stored message when ArrayList is empty`() {
            assertEquals(0, emptyStudent!!.countAllStudent())
            assertTrue(emptyStudent!!.countAllStudent() == 0)
        }

        @Test
        fun `listAllStudents returns student entries when ArrayList has entries stored`() {
            assertEquals(5, populatedStudent!!.countAllStudent())
            val notesString = populatedStudent!!.listAllStudents().lowercase()
            assertTrue(notesString.contains("name1"))
            assertTrue(notesString.contains("name2"))
            assertTrue(notesString.contains("name3"))
            assertTrue(notesString.contains("name4"))
            assertTrue(notesString.contains("name5"))
        }

    }

    @Nested
    inner class Delete {

        @Test
        fun `deleting a staff entry that does not exist, returns null`() {
            assertNull(emptyStaff!!.deleteStaff(0))
            assertNull(populatedStaff!!.deleteStaff(-1))
            assertNull(populatedStaff!!.deleteStaff(9))
        }

        @Test
        fun `deleting a staff entry that exists delete and returns deleted object`() {
            assertEquals(5, populatedStaff!!.countAllStaff())
            assertEquals(swim, populatedStaff!!.deleteStaff(3))
            assertEquals(4, populatedStaff!!.countAllStaff())
            assertEquals(learnKotlin, populatedStaff!!.deleteStaff(5))
            assertEquals(3, populatedStaff!!.countAllStaff())
        }

        @Test
        fun `deleting a student entry that does not exist, returns null`() {
            assertNull(emptyStudent!!.deleteStudent(0))
            assertNull(populatedStudent!!.deleteStudent(-1))
            assertNull(populatedStudent!!.deleteStudent(9))
        }

        @Test
        fun `deleting a student entry that exists deletes and returns deleted object`() {
            assertEquals(5, populatedStudent!!.countAllStudent())
            assertEquals(swim2, populatedStudent!!.deleteStudent(5))
            assertEquals(4, populatedStudent!!.countAllStudent())
            assertEquals(learnKotlin2, populatedStudent!!.deleteStudent(1))
            assertEquals(3, populatedStudent!!.countAllStudent())
        }
    }

    @Nested
    inner class Update {
        @Test
        fun `updating a staff entry that does not exist returns false`(){
            assertFalse(populatedStaff!!.updateStaff(6, Staff("Updating staff", 2, "Work", 0,"Teacher")))
            assertFalse(populatedStaff!!.updateStaff(-1, Staff("Updating staff", 2, "Work", 0,"Other")))
            assertFalse(emptyStaff!!.updateStaff(0, Staff("Updating staff", 2, "Work", 0,"Other")))
        }

        @Test
        fun `updating a staff entry that exists returns true and updates`() {
            assertEquals(swim, populatedStaff?.findStaff(3))
            assertEquals(2, populatedStaff?.findStaff(2)?.staffId)
            assertEquals("Code App", populatedStaff?.findStaff(4)?.name)
            assertEquals("Work", populatedStaff?.findStaff(4)?.staffAddress)

            assertTrue(populatedStaff?.updateStaff(4, Staff("Updating staff", 2, "College", 0,"Teacher"))?: false)
            assertEquals(1, populatedStaff?.findStaff(1)?.staffId)
            assertEquals("Summer Holiday to France", populatedStaff?.findStaff(1)?.name)
            assertEquals("Hobby", populatedStaff?.findStaff(3)?.staffAddress)
        }

        @Test
        fun `updating a student entry that does not exist returns false`(){
            assertFalse(populatedStudent!!.updateStudent(6, Student("Updating student", 2, 2, 0,"address", "record")))
            assertFalse(populatedStudent!!.updateStudent(-1, Student("Updating student", 2, 2, 0,"address", "record")))
            assertFalse(emptyStudent!!.updateStudent(0, Student("Updating student", 2, 2, 0, "address", "record")))
        }

        @Test
        fun `updating a student entry that exists returns true and updates`() {
            assertEquals(swim2, populatedStudent?.findStudent(5))
            assertEquals(2, populatedStudent?.findStudent(2)?.studentId)
            assertEquals("name4", populatedStudent?.findStudent(4)?.name)
            assertEquals("address4", populatedStudent?.findStudent(4)?.address)

            assertTrue(populatedStudent?.updateStudent(4, Student("Updating student", 2, 2, 0, "address", "record"))?: false)
            assertEquals(1, populatedStudent?.findStudent(1)?.studentId)
            assertEquals("name1", populatedStudent?.findStudent(1)?.name)
            assertEquals("address3", populatedStudent?.findStudent(3)?.address)
        }
    }

    @Nested
    inner class Timetable {
        @Test
        fun `generateTimetable for student with staff info`() {
            val student = Student("John Doe", 1, 1, 0, "123 Main St", "record1")
            val staff = Staff("name1",1,"address",1111,"Teacher")
            val staffInfo = schoolAPI.listAllStaff()

            val timetable = schoolAPI.generateTimetable(student, staffInfo)

            val actualName = timetable.lines()[1].substringAfter("TimeTable:").trim()
            assertTrue(actualName.contains("John Doe"))
        }

        @Test
        fun `generateTimetable for student without staff info`() {
            val student = Student("Jane Doe", 2, 1, 0, "456 Oak St", "record2")
            val staffInfo = ""

            val timetable = schoolAPI.generateTimetable(student, staffInfo)

            val actualName = timetable.lines()[1].substringAfter("TimeTable:").trim()
            assertTrue(actualName.contains("Jane Doe"))
        }

        @Test
        fun `getRandomSubject returns a valid subject`() {
            val subject = schoolAPI.getRandomSubject()

            val validSubjects = listOf("English", "Maths", "Geography", "History", "Civics", "Irish")
            assert(validSubjects.contains(subject))
        }
    }

    @Nested
    inner class Report {
        @Test
        fun `generateReport for student with grades`() {
            val student = Student("John Doe", 1, 1, 0, "123 Main St", "record1")
            val grade = Grade(1, 90, 85, 78, 92, 88, 95)

            val report = schoolAPI.generateReport(student, grade)

            assertTrue(report.contains("John Doe"))
            assertTrue(report.contains("English"))
            assertTrue(report.contains("90"))
            assertTrue(report.contains("Maths"))
            assertTrue(report.contains("85"))
        }

        @Test
        fun `generateReport for student without grades`() {
            val student = Student("Jane Doe", 2, 1, 0, "456 Oak St", "record2")
            val grade = null

            val report = schoolAPI.generateReport(student, grade)

            assertTrue(report.contains("Jane Doe"))
            assertTrue(report.contains("English"))
            assertTrue(report.contains("-"))
            assertTrue(report.contains("Maths"))
            assertTrue(report.contains("-"))
        }
    }
}