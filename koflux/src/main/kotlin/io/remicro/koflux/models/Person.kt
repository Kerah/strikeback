package io.remicro.koflux.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class Person(
    val id: String,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss") val created: Date
)
