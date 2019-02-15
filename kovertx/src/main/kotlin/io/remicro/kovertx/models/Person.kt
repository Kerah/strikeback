package io.remicro.kovertx.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*


data class Person(
    val id: String,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss") val created: Date
)
