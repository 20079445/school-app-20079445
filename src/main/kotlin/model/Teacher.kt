package model

/**
 * Teacher
 *
 * @property staff
 * @property subjects
 * @property classroom
 * @property classAssigned
 * @property yearsWithSchool
 * @property jobTitle
 * @property childSafety
 * @constructor Create empty Teacher
 */
data class Teacher(var staff: Int,
                   var subjects: String,
                   var classroom: Int,
                   var classAssigned: Int,
                   var yearsWithSchool: Int,
                   var jobTitle: String,
                   var childSafety: String)
