package ru.sber.serialization

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.sber.serialization.utility.CustomDeserializer

@JsonDeserialize(using = CustomDeserializer::class)
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)
