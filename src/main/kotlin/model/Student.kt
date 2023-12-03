package model

/**
 * Student
 *
 * @property name
 * @property studentId
 * @property year
 * @property Class
 * @property address
 * @property record
 * @property grades
 * @constructor Create empty Student
 */
data class Student(var name: String = "",
                   var studentId: Int = 0,
                   var year: Int = 0,
                   var Class: Int = 0,
                   var address: String = "",
                   var record: String = "",
                   var grades: ArrayList<Grade> = ArrayList())
