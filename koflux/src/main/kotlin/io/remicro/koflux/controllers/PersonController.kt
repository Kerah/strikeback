package io.remicro.koflux.controllers

import io.remicro.koflux.models.Person
import io.remicro.koflux.repository.PersonRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(val repository: PersonRepository) {
    @PutMapping
    fun create(@RequestBody request: Person) = repository.set(request)

    @GetMapping("/{id}")
    fun accept(@PathVariable id: String) = repository.get(id)
}
