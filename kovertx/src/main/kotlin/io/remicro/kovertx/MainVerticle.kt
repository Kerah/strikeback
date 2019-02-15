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

        router.route().handler(BodyHandler.create())
        router.put("/api/v1/persons").handler {
            try {
                Json
                    .decodeValue(it.bodyAsString, Person::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            it.response().putHeader("Content-Type", "application/json; charset=utf-8")
                .end()
                /*.let { obj ->
                    vertx
                        .sharedData()
                        .getLocalMap<String, Person>("person_map")[obj.id] = obj
                }*/

        }
        router.get("/api/v1/persons/:personID").handler {
            val person = vertx
                .sharedData()
                .getLocalMap<String, Person>("person_map")[it.request().getParam("personID")]
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