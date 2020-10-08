package furhatos.app.mathtutor.nlu

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.util.Language

class Name(var name : String = "") : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("@name", "My name is @name", "I am @name")
    }
}