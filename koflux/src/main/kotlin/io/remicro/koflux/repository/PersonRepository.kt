package io.remicro.koflux.repository

import io.remicro.koflux.models.Person
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap

@Component
class PersonRepository {
    private val dataSet = ConcurrentHashMap<String, Person>()
    
    fun get(key: String) = dataSet.get(key)!!.toMono()

    fun set(person: Person) = Mono.fromCallable {
        dataSet[person.id] = person
        "OK"
    }
}
