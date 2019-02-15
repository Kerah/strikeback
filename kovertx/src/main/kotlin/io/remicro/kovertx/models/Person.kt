package io.remicro.kovertx.models

import com.fasterxml.jackson.annotation.JsonFormat
import io.vertx.core.shareddata.Shareable
import java.util.*


data class Person(
    val id: String,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss") val created: Date
): Shareable
