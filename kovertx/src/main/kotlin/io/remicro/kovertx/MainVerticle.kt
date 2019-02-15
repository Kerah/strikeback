package io.remicro.kovertx

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.remicro.kovertx.models.Person
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import java.lang.Exception

class MainVerticle : AbstractVerticle() {
    override fun start(startFuture: Future<Void>) {
        Json.mapper.registerModule(KotlinModule())
        
        val httpServer = vertx.createHttpServer()
        val router = Router.router(vertx)

        val sd = vertx.sharedData().getLocalMap<String, Person>("person_map")

        router.route().handler(BodyHandler.create())
        router.put("/api/v1/persons").handler {
            try {
                val obj = Json
                    .decodeValue(it.bodyAsString, Person::class.java)
                sd[obj.id] = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }
            it.response().putHeader("Content-Type", "application/json; charset=utf-8")
                .end()
        }
        router.get("/api/v1/persons/:personID").handler {
            val person = sd[it.request().getParam("personID")]
            it
                .response()
                .putHeader("Content-Type", "application/json; charset=utf-8")
                .end(Json.encode(person))
        }.produces("application/json")



        httpServer
            .requestHandler(router)
            .listen(8080) { http ->
                if (http.succeeded()) {
                    startFuture.complete()
                    println("HTTP server started on port 8080")
                } else {
                    startFuture.fail(http.cause())
                }
            }
    }
}