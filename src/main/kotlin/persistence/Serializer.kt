package persistence

interface Serializer<T> {
    @Throws(Exception::class)
    fun writeStaff(obj: Any?)

    @Throws(Exception::class)
    fun writeStudent(obj: Any?)

    @Throws(Exception::class)
    fun readStaff(): Any?

    @Throws(Exception::class)
    fun readStudent(): Any?
}