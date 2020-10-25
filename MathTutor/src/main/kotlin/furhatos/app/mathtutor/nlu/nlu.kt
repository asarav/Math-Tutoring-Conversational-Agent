package furhatos.app.mathtutor.nlu

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.util.Language

class Name(var name : String = "") : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("@name", "My name is @name", "I am @name")
    }
}

class AnotherExerciseOfSameLevel() : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("I want another exercise of the same level", "Same Level", "Another exercise")
    }
}

class ExerciseOfMoreAdvancedLevel() : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("I want another exercise of a more advanced level", "Advanced Level", "Something more advanced", "More advanced")
    }
}

class DoneWithExercises() : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("I'm done practicing", "I want to quit", "I'm done", "I am done practicing", "I am done", "done", "quit", "done practicing")
    }
}