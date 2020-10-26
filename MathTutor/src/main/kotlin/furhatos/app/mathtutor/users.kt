package furhatos.app.mathtutor

import furhatos.app.mathtutor.flow.Start
import furhatos.records.User
import furhatos.flow.kotlin.*

class UserData(
        var name : String = "",
        var frustration : Int = 0,
        var difficulty : Int = 0,
        var answer : Int = 0,
        var totalStates : Int = 0,
        var rightAnswers : Int = 0,
        var wrongAnswers : Int = 0,
        var numberOfExplanations : Int = 0,
        var nextState: State = Start
)

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())