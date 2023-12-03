package persistence

/**
 * Generic interface for serializing and deserializing objects.
 *
 * @param T the type of the object to be serialized/deserialized.
 */
interface Serializer<T> {
    /**
     * Write objects of type T to a storage medium.
     *
     * @param obj the object to be written.
     * @throws Exception if an error occurs during the writing process.
     */
    @Throws(Exception::class)
    fun writeStaff(obj: Any?)

    /**
     * Write objects of type T to a storage medium.
     *
     * @param obj the object to be written.
     * @throws Exception if an error occurs during the writing process.
     */
    @Throws(Exception::class)
    fun writeStudent(obj: Any?)

    /**
     * Write objects of type T to a storage medium.
     *
     * @param obj the object to be written.
     * @throws Exception if an error occurs during the writing process.
     */
    @Throws(Exception::class)
    fun writeGrade(obj: Any?)

    /**
     * Write objects of type T to a storage medium.
     *
     * @param obj the object to be written.
     * @throws Exception if an error occurs during the writing process.
     */
    @Throws(Exception::class)
    fun writeTeacher(obj: Any?)

    /**
     * Read objects of type T from a storage medium.
     *
     * @return the read object of type T.
     * @throws Exception if an error occurs during the reading process.
     */
    @Throws(Exception::class)
    fun readStaff(): Any?

    /**
     * Read objects of type T from a storage medium.
     *
     * @return the read object of type T.
     * @throws Exception if an error occurs during the reading process.
     */
    @Throws(Exception::class)
    fun readStudent(): Any?

    /**
     * Read objects of type T from a storage medium.
     *
     * @return the read object of type T.
     * @throws Exception if an error occurs during the reading process.
     */
    @Throws(Exception::class)
    fun readGrade(): Any?

    /**
     * Read objects of type T from a storage medium.
     *
     * @return the read object of type T.
     * @throws Exception if an error occurs during the reading process.
     */
    @Throws(Exception::class)
    fun readTeacher(): Any?
}
