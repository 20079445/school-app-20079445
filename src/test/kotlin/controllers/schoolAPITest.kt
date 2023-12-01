package controllers

import model.Staff
import model.Student
import controller.SchoolAPI
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals

class SchoolAPITest() {
    private var learnKotlin: Staff? = null
    private var summerHoliday: Staff? = null
    private var codeApp: Staff? = null
    private var testApp: Staff? = null
    private var swim: Staff? = null
    private var populatedStaff: SchoolAPI? = SchoolAPI(XMLSerializer(File("Staff.xml"), File("Student.xml")))
    private var emptyStaff: SchoolAPI? = SchoolAPI(XMLSerializer(File("Staff.xml"), File("Student.xml")))


    @BeforeEach
    fun setup() {
        learnKotlin = Staff("Learning Kotlin", 5, "College", 0, 0)
        summerHoliday = Staff("Summer Holiday to France", 1, "Holiday", 0, 0)
        codeApp = Staff("Code App", 4, "Work", 0, 0)
        testApp = Staff("Test App", 2, "Work", 0, 0)
        swim = Staff("Swim - Pool", 3, "Hobby", 0, 0)


        populatedStaff!!.addStaff(learnKotlin!!)
        populatedStaff!!.addStaff(summerHoliday!!)
        populatedStaff!!.addStaff(codeApp!!)
        populatedStaff!!.addStaff(testApp!!)
        populatedStaff!!.addStaff(swim!!)
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
    }

    @Nested
    inner class AddStaff {
        @Test
        fun `adding a staff entry to a populated list adds to ArrayList`() {
            val newStaff = Staff("Study Lambdas", 6, "College", 0, 0)
            assertEquals(5, populatedStaff!!.countAllStaff())
            assertTrue(populatedStaff!!.addStaff(newStaff))
            assertEquals(6, populatedStaff!!.countAllStaff())
            assertEquals(newStaff, populatedStaff!!.findStaff(6))
        }

        @Test
        fun `adding a staff entry to an empty list`() {
            val newStaff = Staff("Study Lambdas", 1, "College", 0, 0)
            assertEquals(0, emptyStaff!!.countAllStaff())
            assertTrue(emptyStaff!!.addStaff(newStaff))
            assertEquals(1, emptyStaff!!.countAllStaff())
            assertEquals(newStaff, emptyStaff!!.findStaff(1))
        }
    }

    @Nested
    inner class ListNotes {

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
    }

    @Nested
    inner class DeleteNotes {

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
    }

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a staff entry that does not exist returns false`(){
            assertFalse(populatedStaff!!.updateStaff(6, Staff("Updating staff", 2, "Work", 0,0)))
            assertFalse(populatedStaff!!.updateStaff(-1, Staff("Updating staff", 2, "Work", 0,0)))
            assertFalse(emptyStaff!!.updateStaff(0, Staff("Updating staff", 2, "Work", 0,0)))
        }

        @Test
        fun `updating a staff entry that exists returns true and updates`() {
            assertEquals(swim, populatedStaff?.findStaff(3))
            assertEquals(2, populatedStaff?.findStaff(2)?.staffId)
            assertEquals("Code App", populatedStaff?.findStaff(4)?.name)
            assertEquals("Work", populatedStaff?.findStaff(4)?.staffAddress)

            assertTrue(populatedStaff?.updateStaff(4, Staff("Updating staff", 2, "College", 0,0))?: false)
            assertEquals(1, populatedStaff?.findStaff(1)?.staffId)
            assertEquals("Summer Holiday to France", populatedStaff?.findStaff(1)?.name)
            assertEquals("Hobby", populatedStaff?.findStaff(3)?.staffAddress)
        }
    }
}