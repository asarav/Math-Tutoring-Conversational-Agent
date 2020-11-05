package furhatos.app.mathtutor.flow

import furhatos.app.mathtutor.nlu.*
import furhatos.app.mathtutor.userData
import furhatos.autobehavior.userSpeechStartGesture
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.*
import furhatos.nlu.common.Number

//Beginning of introduction section

val Start : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)
        
        furhat.ask("Hello! How are you doing? What is your name?")
        furhat.userSpeechStartGesture = listOf(Gestures.Thoughtful, Gestures.Nod, Gestures.Smile)

    }

    onResponse<Name> {
        if (it.intent.name != null) {
            users.current.userData.name = it.intent.name
            goto(NameState)
        } else {
            propagate()
        }
    }
/*
    onResponse<TellName> {
        System.out.println(it.intent.name)
    }
 */
}

val NameState : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

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
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

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
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.ask("Awesome " + users.current.userData.name + ", nice to meet you! " +
        "I'm TutorBot. Today I will teach you the math skill of division. First I will explain to you the concept of division and show you some examples. After the explanation and examples I will give you division problems that you can solve to practice. Don't worry if you don't understand it the first time, I will help you through the process! " +
                "Are you ready to start " + users.current.userData.name + "?")
    }

    onResponse<Yes> {
        goto(Explanation1)
    }

    onResponse<No> {
        propagate()
    }
}

//Beginning of explanations section

val Explanation1 : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++
        users.current.userData.numberOfExplanations++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("Division is a method of distributing a group of things into equal parts. It is one of the four basic operations of arithmetic, which gives a fair result of sharing.")
        delay(1000) // Pausing for 2000 ms
        furhat.say("I'll give you an example: Imagine there are 12 chocolates, and 3 friends want to share them equally, how do they divide the chocolates?")
        delay(4000)
        furhat.ask("The question is: what is 12 chocolates divided by 3 friends? The answer is: They get 4 each. So 12 divided by 3 is 4. Do you understand this?")
    }

    onResponse<Yes> {
        if (users.current.userData.numberOfExplanations == 1) {
            users.current.userData.difficulty = 2
        }
        goto(UserUnderstandsAfterFirstExplanation)
    }

    onResponse<No> {
        if (users.current.userData.numberOfExplanations == 1) {
            users.current.userData.difficulty = 1
        }
        goto(FamiliarWithMultiplication)
    }
}

val UserUnderstandsAfterFirstExplanation : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)
        if (users.current.userData.numberOfExplanations == 1) {
            furhat.ask("Great! You're a fast learner, " + users.current.userData.name + ". Do you want me to show you another example?")
        }
        else {
            furhat.ask("That is great, " + users.current.userData.name + ". Do you want me to show you another example?")
        }
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
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.ask("That is no problem at all! We can take things slowly. I will try to explain it to you in a different way. Are you familiar with multiplication?")
    }

    onResponse<Yes> {
        goto(Explanation2)
    }

    onResponse<No> {
        users.current.userData.difficulty = 0
        goto(Explanation3)
    }
}

val Explanation2 : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++
        users.current.userData.numberOfExplanations++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("Great! Multiplication is actually the opposite of division, or we can call it the reverse of multiplication."
                + " In multiplication, we want to know the total of groups of numbers. So if we want to know the total of 2 groups of 4, we multiply 2 by 4 which results into 8."
                + " In division, we want to divide the total into a few groups and we want to know how many are in each group."
                + " Imagine that we want to divide 8 into 2 equal groups, we want to know how many there are in each group. So 8 divided by 2 is 4. I'll give you another example.")
        goto(Example2)
    }
}

val Explanation3 : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++
        users.current.userData.numberOfExplanations++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("Alright, then I'll explain it this way: "
                + "In division, we want to divide the total into a few equal groups and we want to know how many are in each group."
                + " Or we can say: We want to divide the total into groups of a certain number and we want to know how many groups there are.")
        goto(Example2)
    }
}

val Example2 : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("Imagine that we have a total of 10 students, and we want to divide them into 2 classrooms. How many people will there be in each class room? So, what is 10 divided by 2?")
                delay(4000)
        furhat.say("The answer is 5. If we divide 10 students by 2 classrooms, each classroom will have 5 students.")

        random(furhat.ask("Do you understand how division works?"),
                furhat.ask("Do you see what I mean?"),
                furhat.ask("Do you understand it?"),
                furhat.ask("Are you able to follow it?"))
    }

    onResponse<Yes> {
        users.current.userData.frustration = 0
        goto(UserUnderstands)
    }

    onResponse<No> {
        //Dead End?
        users.current.userData.frustration = 2
        furhat.say("Don't worry, " + users.current.userData.name + ", it is completely normal if you are struggling to understand it. Don't give up, you will get there! I will take time to explain it to you again.")
        goto(Explanation1)
    }
}

val UserUnderstands : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

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
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("What is 24 divided by 4?")
        delay(4000)
        furhat.say("The question here is: how can we equally divide 24 into 4 groups? The answer is 6. 24 divided by 4 is 6.")

        goto(ReadyForExercises)
    }
}

val ReadyForExercises : State = state(Interaction) {
    onEntry{
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("Alright! Let's jump into some exercises. You can use pen and paper if you want. I will formulate a division exercise, you can tell me the answer when you have solved it.")
        goto(Exercises)
    }
}

//Beginning of Exercises Section
val easyExercises : List<Pair<Int, Int>> = listOf(Pair(15, 3), Pair(14, 2), Pair(8, 2), Pair(8, 2), Pair(9, 3), Pair(12, 6), Pair(6, 3), Pair(8, 4), Pair(10, 5), Pair(6, 2), Pair(15, 5))
val mediumExercises : List<Pair<Int, Int>> = listOf(Pair(72, 8), Pair(24, 6), Pair(32, 8), Pair(55, 5), Pair(42, 7), Pair(56, 8), Pair(36, 6), Pair(81, 9), Pair(63, 7), Pair(54, 9), Pair(40, 5))
val hardExercises : List<Pair<Int, Int>> = listOf(Pair(112, 8), Pair(108, 9), Pair(90, 6), Pair(117, 9), Pair(78, 6), Pair(112, 7), Pair(64, 4), Pair(91, 7), Pair(10, 5), Pair(6, 2), Pair(15, 5))

val Exercises : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++
        users.current.userData.answer = 0
        val exercise = users.current.userData.currentExercise

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " name: " + this.thisState.name +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        if(users.current.userData.difficulty == 0) {
            users.current.userData.answer = easyExercises[exercise].first / easyExercises[exercise].second
            furhat.ask("What is " + easyExercises[exercise].first + " divided by " + easyExercises[exercise].second + "?")
        } else if(users.current.userData.difficulty == 1) {
            users.current.userData.answer = mediumExercises[exercise].first / mediumExercises[exercise].second
            furhat.ask("What is " + mediumExercises[exercise].first + " divided by " + mediumExercises[exercise].second + "?")
        } else {
            users.current.userData.answer = hardExercises[exercise].first /hardExercises[exercise].second
            furhat.ask("What is " + hardExercises[exercise].first + " divided by " + hardExercises[exercise].second + "?")
        }
    }

    onResponse<Number> {
        if(it.intent.value == users.current.userData.answer) {
            furhat.gesture(Gestures.Nod)
            goto(RightAnswer)
        } else {
            furhat.gesture(Gestures.Shake)
            goto(WrongAnswer)
        }
    }
}

val RightAnswer : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++
        users.current.userData.rightAnswers++

        if (users.current.userData.frustration >= 1) {
            users.current.userData.frustration--
        }
        else if (users.current.userData.numberOfExplanations == 1 && users.current.userData.rightAnswers > 1 &&
                users.current.userData.wrongAnswers == 0 && users.current.userData.frustration < 2) {
            users.current.userData.frustration++
        }

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("That is correct.")
        random({furhat.say("Well done, " + users.current.userData.name + ".") },
                {furhat.say("You are doing great, " + users.current.userData.name + "!") })

        // If student is frustrated
        if (users.current.userData.getCombinedFrustration() >= 2) {
            val intelligence = users.current.userData.difficulty

            if (intelligence == 1) { // Medium intelligence
                random({furhat.say("You're a natural!")}, {furhat.say("You're a quick learner!")})
                furhat.gesture(Gestures.BigSmile)
                delay(1000)
                goto(Continuation)
            } else if (intelligence == 2){ // High intelligence
                goto(FrustratonHighIntelligence)
            } else {
                goto(Continuation)
            }
        } else {
            goto(Continuation)
        }
    }
}

val WrongAnswer : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++
        users.current.userData.wrongAnswers++

        if (users.current.userData.frustration < 2) {
            users.current.userData.frustration++
        }

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("That is not the correct answer, the correct answer is " + users.current.userData.answer + ".")
        random({furhat.say("Don't worry " + users.current.userData.name + ", you will definitely get there!") })

        // If student is frustrated
        if (users.current.userData.getCombinedFrustration() >= 2) {

            val intelligence = users.current.userData.difficulty

            // Low intelligence
            if (intelligence == 0) {
                users.current.userData.nextState = Continuation
                random({goto(FrustrationLowIntelligenceMoreExplanation)}, {goto(FrustrationLowIntelligenceEncouragement)})
            } else if (intelligence == 1) { // Medium intelligence
                random({furhat.say("It's no big deal, everyone makes mistakes!")}, {furhat.say("Making mistakes is part of learning.")})
                furhat.gesture(Gestures.BigSmile)
                furhat.ask("Do you want me to go over the explanation of the division concept?")
            } else { // High intelligence
                // Don't go back to explanation, instead just ask for another difficulty.
                goto(Continuation)
            }
        } else {
            furhat.ask("Do you want me to go over the explanation of the division concept?")
        }
    }

    onResponse<Yes> {
        goto(Explanation1)
    }

    onResponse<No> {
        goto(Continuation)
    }
}

val Continuation : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++
        users.current.userData.currentExercise = (0..easyExercises.size).random()

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        if(users.current.userData.difficulty == 0) {
            furhat.ask("Would you like to try another exercise of the same level, or something more advanced? If you're done practicing, please tell me.")
        }
        else if(users.current.userData.difficulty == 1) {
            furhat.ask("Would you like to try another exercise of the same level, something easier, or something more advanced? If you're done practicing, please tell me.")
        }
        else {
            furhat.ask("Would you like to try another exercise of the same level, or something easier? If you're done practicing, please tell me.")
        }
    }

    onResponse<AnotherExerciseOfSameLevel> {
        goto(Exercises)
    }

    onResponse<AnotherExerciseOfEasierLevel> {
        users.current.userData.difficulty--
        goto(Exercises)
    }


    onResponse<ExerciseOfMoreAdvancedLevel> {
        users.current.userData.difficulty++
        goto(Exercises)
    }

    onResponse<DoneWithExercises> {
        goto(FinalState)
    }
}

/**
 * START - 'Frustration based states'
 */
val FrustrationLowIntelligenceEncouragement : State = state(Interaction) {
    onEntry {

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("${furhat.voice.emphasis("Learning a new task can be very difficult " + users.current.userData.name)}")
        furhat.gesture(Gestures.BigSmile)
        delay(500)
        furhat.say("Making mistakes is part of the learning process.")
        furhat.say("I'm ${furhat.voice.emphasis("sure")} you will learn it!")
        delay(1000)
        goto(users.current.userData.nextState)
    }
}

val FrustrationLowIntelligenceMoreExplanation: State = state(Interaction) {
    onEntry {
        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("${furhat.voice.emphasis("I understand this is difficult for you " + users.current.userData.name)}")
        furhat.say("But you're not alone, I'll guide you through it!")
        furhat.say("I'm ${furhat.voice.emphasis("sure")} you will learn it!")
        furhat.gesture(Gestures.BigSmile)
        delay(500)
        furhat.ask("Do you prefer some extra explanation?")
    }

    onResponse<Yes> {
        goto(Explanation3)
    }

    onResponse<No> {
        furhat.say("Okay, I understand! Let's continue then.")
        goto(Continuation)
    }
}

val FrustratonHighIntelligence : State = state(Interaction) {
    onEntry {

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("${furhat.voice.emphasis("Great job so far " + users.current.userData.name)}")
        furhat.gesture(Gestures.Nod)
        furhat.say("I see this is quite easy for you.")
        delay(500)
        furhat.ask("Instead, do you prefer to solve a special riddle?")
        furhat.gesture(Gestures.BigSmile)
    }

    onResponse<Yes> {
        furhat.say("Awesome!")
        goto(FrustrationRiddle)
    }

    onResponse<No> {
        furhat.say("I understand. Let's go back to the exercises.")
        goto(Continuation)
    }
}

val FrustrationRiddle: State = state(Interaction) {
    var reask = 0

    onEntry {

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("So the riddle is like this:")
        furhat.ask("How do you make the number seven even ${furhat.voice.pause("200ms")} without substraction, multiplication or division?")
    }

    onResponse<RiddleAnswer> {
        furhat.gesture(Gestures.Nod)
        furhat.say("That's correct.")
        furhat.say("You're a natural. I really can't learn you anything today!")
        delay(1000)
        goto(FinalState)
    }

    onResponse {
        if (reask == 0) {
            furhat.say("I don't think that is the correct answer or I didn't understand you.")
            furhat.say("Let me repeat myself.")
            reask += 1 // Make sure we won't repeat for the 3rd time.
            this.reentry()
        } else {
            furhat.say("I don't think that is the correct answer or I didn't understand you properly.")
            goto(FrustrationRiddleHint)
        }
    }

    onNoResponse {
        if (reask == 0) {
            reask += 1
            furhat.say("Let me repeat myself.")
            this.reentry()
        } else {
            furhat.say("It's not the easiest riddle!")
            goto(FrustrationRiddleHint)
        }
    }
}

val FrustrationRiddleHint: State = state(Interaction) {
    onEntry {
        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("Let me give you a hint.")
        furhat.say("Focus on the word seven!")
        delay(1000)
        furhat.ask("What is the answer?")
    }

    onResponse<RiddleAnswer> {
        furhat.gesture(Gestures.Nod)
        furhat.say("That's correct.")
        furhat.say("You're a natural. I really can't learn you anything today!")
        delay(1000)
        goto(FinalState)
    }

    onResponse {
        furhat.say("Maybe this riddle was a bit mean.")
        furhat.say("The answer is: drop the S")
        furhat.gesture(Gestures.BigSmile)
        delay(1000)
        furhat.say("I still think you're a great learner though!")
        goto(FinalState)
    }

    onNoResponse {
        furhat.say("Maybe this riddle was a bit mean.")
        furhat.say("The answer is: drop the S")
        furhat.gesture(Gestures.BigSmile)
        delay(1000)
        furhat.say("I still think you're a great learner though!")
        goto(FinalState)
    }
}

val FinalState : State = state(Interaction) {
    onEntry {
        users.current.userData.totalStates++

        println("State: " + this.thisState.name + " - frustration: " + users.current.userData.frustration +
                " model frustration: " + users.current.userData.modelFrustration +
                " combined frustration: " + users.current.userData.combinedFrustration +
                " total states: " + users.current.userData.totalStates +
                " number explanations: " + users.current.userData.numberOfExplanations +
                " difficulty: " + users.current.userData.difficulty +
                " right answers: " + users.current.userData.rightAnswers)

        furhat.say("Alright. Thank you for your attention "+ users.current.userData.name + ". Have a nice day!")
        delay(5000)
        goto(Start)
    }
}
