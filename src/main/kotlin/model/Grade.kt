package model

/**
 * Grade
 *
 * @property student
 * @property english
 * @property maths
 * @property geography
 * @property history
 * @property civics
 * @property irish
 * @constructor Create empty Grade
 */
data class Grade(var student: Int = 0,
                 var english: Int = 0,
                 var maths: Int = 0,
                 var geography: Int = 0,
                 var history: Int = 0,
                 var civics: Int = 0,
                 var irish: Int = 0)