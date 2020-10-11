package furhatos.app.mathtutor.flow

import furhatos.app.mathtutor.nlu.*
import furhatos.app.mathtutor.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*

val Start : State = state(Interaction) {
    onEntry {
        furhat.ask("Hello! How are you doing? What is your name?")
    }

    onResponse<Name> {
        if (it.intent.name != null) {
            users.current.userData.name = it.intent.name
            goto(NameState)
        } else {
            propagate()
        }
    }
}

val NameState : State = state(Interaction) {
    onEntry {
        furhat.ask("So your name is " + users.current.userData.name + ", is that correct?")
    }

    onResponse<Yes> {
        goto(IntroductionState)
    }

    onResponse<No> {
        goto(NameWrongState)
    }
}

val NameWrongState : State = state(Interaction) {
    onEntry {
        furhat.ask("I'm sorry! Could you say your name again?")
    }

    onResponse<Name> {
        if (it.intent.name != null) {
            users.current.userData.name = it.intent.name
            goto(NameState)
        } else {
            propagate()
        }
    }
}

val IntroductionState : State = state(Interaction) {
    onEntry {
        furhat.ask("Awesome " + users.current.userData.name + ", nice to meet you! " +
        "I'm TutorBot. Today I will teach you the math skill division. First I will explain to you the concept of division and show you some examples. After the explanation and examples I will give you division problems that you can solve to practice. Don't worry if you don't understand it the first time, I will help you through the process!" +
                "Are you ready to start " + users.current.userData.name + "?")
    }

    onResponse<Yes> {
        goto(Explanation1)
    }

    onResponse<No> {
        propagate()
    }
}

val Explanation1 : State = state(Interaction) {
    onEntry {
        furhat.say("Division is a method of distributing a group of things into equal parts. It is one of the four basic operations of arithmetic, which gives a fair result of sharing.")
        delay(1000) // Pausing for 2000 ms
        furhat.say("I'll give you an example: Imagine there are are 12 chocolates, and 3 friends want to share them equally, how do they divide the chocolates?")
        delay(3000)
        furhat.ask("The question is: what is 12 chocolates divided by 3 friends? The answer is: They get 4 each. So 12 divided by 3 is 4.Do you understand this?")
    }

    onResponse<Yes> {
        goto(UserUnderstandsAfterFirstExplanation)
    }

    onResponse<No> {
        goto(FamiliarWithMultiplication)
    }
}

val UserUnderstandsAfterFirstExplanation : State = state(Interaction) {
    onEntry {
        furhat.ask("Great! You're a fast learner, "+ users.current.userData.name + ". Do you want me to show you another example?")
    }

    onResponse<Yes> {
        goto(Example3)
    }

    onResponse<No> {
        goto(ReadyForExercises)
    }
}

val FamiliarWithMultiplication : State = state(Interaction) {
    onEntry {
        furhat.ask("That is no problem at all! We can take things slowly. I will try to explain it to you in a different way. Are you familiar with multiplication?")
    }

    onResponse<Yes> {
        goto(Explanation2)
    }

    onResponse<No> {
        goto(Explanation3)
    }
}

val Explanation2 : State = state(Interaction) {
    onEntry {
        furhat.say("Great! Multiplication is actually the opposite of division, or we can call it the reverse of multiplication."
                + "In multiplication, we want to know the total of groups of numbers. So if we want to know the total of 2 groups of 4, we multiply 2 by 4 which results into 8."
                + "In division, we want to divide the total into a few groups and we want to know how many are in each group."
                + " Imagine that we want to divide 8 into 2 equal groups, we want to know how many there are in each group. So 8 divided by 2 is 4. I'll give you another example.")
        goto(Example2)
    }
}

val Explanation3 : State = state(Interaction) {
    onEntry {
        furhat.say("Alright, then I'll explain it this way: "
                + "In division, we want to divide the total into a few equal groups and we want to know how many are in each group."
                + " Or we can say: We want to divide the total into groups of a certain number and we want to know how many groups there are.")
        goto(Example2)
    }
}

val Example2 : State = state(Interaction) {
    onEntry {
        furhat.say("Imagine that we have a total of 10 students, and we want to divide them into 2 classrooms. How many people will there be in each class room? So, what is 10 divided by 2?")
                delay(3000)
        furhat.say("The answer is 5. If we divide 10 students by 2 classrooms, each classroom will have 5 students.")

        random(furhat.ask("Do you understand how division works?"),
                furhat.ask("Do you see what I mean?"),
                furhat.ask("Do you understand it?"),
                furhat.ask("Are you able to follow it?"))
    }

    onResponse<Yes> {
        goto(UserUnderstands)
    }

    onResponse<No> {
        //Dead End?
        furhat.say("Don't worry, " + users.current.userData.name + ", it is completely normal if you are struggling to understand it. Don't give up, you will get there! I will take time to explain it to you again.")
    }
}

val UserUnderstands : State = state(Interaction) {
    onEntry {
        furhat.ask("That's great! Good job, " + users.current.userData.name +". Do you want to see another example")
    }

    onResponse<Yes> {
        goto(Example3)
    }

    onResponse<No> {
        goto(ReadyForExercises)
    }
}

val Example3 : State = state(Interaction) {
    onEntry {
        furhat.say("What is 24 divided by 4?")
        delay(3000)
        furhat.say("The question here is: how can we equally divide 24 into 4 groups? The answer is 6. 24 divided by 4 is 6.")

        goto(ReadyForExercises)
    }
}

val ReadyForExercises : State = state(Interaction) {
    onEntry{
        furhat.say("Alright! Let's jump into some exercises.")
    }
}