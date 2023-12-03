package model

data class Staff(var name: String,
                 var staffId: Int,
                 var staffAddress: String,
                 var staffPhone: Int,
                 var typeOfStaff: String,
                 var teachers: ArrayList<Teacher> = ArrayList())
