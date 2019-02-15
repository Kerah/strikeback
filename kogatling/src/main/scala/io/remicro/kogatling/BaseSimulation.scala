package io.remicro.kogatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BaseSimulation extends Simulation {
  val baseUrl = s"http://localhost:8080"

  val httpConf = http.baseURL(baseUrl)

  val headers = Map("Accept" -> """application/json""")

  val healthPage = during(1 minutes) {
    exec(http("health")
      .get("/api/v1/persons/a.govnoedov"))
  }
  

  val scn = scenario("Get the health page")
      .exec(healthPage)
  
  setUp(
    scn.inject(
      atOnceUsers(200)
    ).throttle(reachRps(100000) in (10 seconds), holdFor(1 minute))
  ).protocols(httpConf).disablePauses
}
