package model

data class Student(var name: String = "",
                   var studentId: Int = 0,
                   var year: Int = 0,
                   var address: String = "",
                   var record: String = "",
                   var grades: ArrayList<Grade> = ArrayList())
