package furhatos.app.mathtutor

import furhatos.records.User

class UserData(
        var name : String = "",
        var frustration : Int = 0,
        var difficulty : Int = 0,
        var answer : Int = 0
)

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())