package furhatos.app.mathtutor

import furhatos.app.fruitseller.AffwildModel
import furhatos.records.User

class UserData(
        var name : String = "",
        var frustration : Int = 0,
        var difficulty : Int = 0,
        var answer : Int = 0,
        var totalStates : Int = 0,
        var rightAnswers : Int = 0,
        var wrongAnswers : Int = 0,
        var numberOfExplanations : Int = 0,
        var currentExercise : Int = 0

) {
    fun getCombinedFrustration(): Int {
        return ((frustration + AffwildModel.updateAndGetFrustration()) / 2).toInt()
    }
}

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())