package furhatos.app.mathtutor.flow

import furhatos.app.mathtutor.nlu.*
import furhatos.app.mathtutor.order
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*

val Start = state(Interaction) {
    onEntry {
        random(
            {   furhat.say("Hello, I am your Math Tutor") },
            {   furhat.say("Oh, hello there. I am your math tutor") }
        )

        goto(Confidence)
    }
}

val Confidence = state() {
    onEntry {
        furhat.ask("Are you confident in your math skills?")
    }

    onResponse<Yes> {
        furhat.say("Great! Let's put your skills to the test.")
        goto(Knowledge)
    }

    onResponse<No> {
        furhat.say("Okay, we can take things slowly.")
        goto(Knowledge)
    }
}

val Knowledge = state() {
    onEntry {
        furhat.ask("Before we start, do you know how to compute fractions?")
    }

    onResponse<Yes> {
        furhat.say("Great! Then let's jump into some exercises")
        goto(Idle)
    }

    onResponse<No> {
        furhat.say("Okay, then let's begin with an overview of how to compute fractions")
        goto(Idle)
    }
}

val Options = state(Interaction) {
    onResponse<BuyFruit> {
        val fruits = it.intent.fruits
        if (fruits != null) {
            goto(OrderReceived(fruits))
        }
        else {
            propagate()
        }
    }

    onResponse<RequestOptions> {
        furhat.say("We have ${Fruit().optionsToText()}")
        furhat.ask("Do you want some?")
    }

    onResponse<Yes> {
        random(
                { furhat.ask("What kind of fruit do you want?") },
                { furhat.ask("What type of fruit?") }
        )
    }
}

fun OrderReceived(fruits: FruitList) : State = state(Options) {
    onEntry {
        furhat.say("${fruits.text}, what a lovely choice!")
        fruits.list.forEach {
            users.current.order.fruits.list.add(it)
        }
        furhat.ask("Anything else?")
    }

    onReentry {
        furhat.ask("Did you want something else?")
    }

    onResponse<No> {
        furhat.say("Okay, here is your order of ${users.current.order.fruits}. Have a great day!")
    }
}