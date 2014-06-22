package bootstrap

import rest.RestService
import spray.can.Http
import akka.io.IO
import akka.actor.{ActorSystem, Props}

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("rest-service-actor-system")

  // create and start our service actor
  val service = system.actorOf(Props[RestService], "rest-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, "localhost", port = 8080)

}
