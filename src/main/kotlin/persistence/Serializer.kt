package persistence

interface Serializer<T> {
    @Throws(Exception::class)
    fun writeStaff(obj: Any?)

    @Throws(Exception::class)
    fun writeStudent(obj: Any?)

    @Throws(Exception::class)
    fun writeGrade(obj: Any?)

    @Throws(Exception::class)
    fun writeTeacher(obj: Any?)

    @Throws(Exception::class)
    fun readStaff(): Any?

    @Throws(Exception::class)
    fun readStudent(): Any?

    @Throws(Exception::class)
    fun readGrade(): Any?

    @Throws(Exception::class)
    fun readTeacher(): Any?
}