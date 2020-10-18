package furhatos.app.mathtutor

import furhatos.app.fruitseller.AffwildModel
import furhatos.app.mathtutor.flow.Idle
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class FruitsellerSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    AffwildModel.getOutputs()
    Skill.main(args)
}
