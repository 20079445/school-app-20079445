package model

/**
 * Staff
 *
 * @property name
 * @property staffId
 * @property staffAddress
 * @property staffPhone
 * @property typeOfStaff
 * @property teachers
 * @constructor Create empty Staff
 */
data class Staff(var name: String,
                 var staffId: Int,
                 var staffAddress: String,
                 var staffPhone: Int,
                 var typeOfStaff: String,
                 var teachers: ArrayList<Teacher> = ArrayList())
