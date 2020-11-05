package furhatos.app.mathtutor

import furhatos.app.fruitseller.AffwildModel
import furhatos.app.mathtutor.flow.Start
import furhatos.records.User
import furhatos.flow.kotlin.*
import kotlin.math.max

class UserData(
        var name : String = "",
        var frustration : Int = 0,
        var modelFrustration : Double = 0.0,
        var combinedFrustration : Double = 0.0,
        var difficulty : Int = 0,
        var answer : Int = 0,
        var totalStates : Int = 0,
        var rightAnswers : Int = 0,
        var wrongAnswers : Int = 0,
        var numberOfExplanations : Int = 0,
        var nextState: State = Start,
        var currentExercise : Int = 0

) {
    fun getCombinedFrustration(): Int {
        modelFrustration = AffwildModel.updateAndGetFrustration()
        combinedFrustration = max(frustration.toDouble(), modelFrustration)
        return combinedFrustration.toInt()
    }
}

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())