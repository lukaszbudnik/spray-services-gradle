package rest

import akka.actor.{ActorContext, Actor}
import spray.httpx.Json4sJacksonSupport
import org.json4s.{FieldSerializer, DefaultFormats}
import model.{Item, ItemCreated, ItemOrder}
import spray.routing.HttpService
import spray.http.{MediaTypes, StatusCodes, Uri}
import service.ItemService

class RestService extends Actor with StatusRoutes with RestRoutesV1 with RestRoutesV2 with JsonProtocol {

  def actorRefFactory = context

  def receive = runRoute(statusRoutes ~ routesV1 ~ routesV2)

}

trait JsonProtocol extends Json4sJacksonSupport {
  implicit def json4sJacksonFormats = DefaultFormats +
    FieldSerializer[Status]() + FieldSerializer[ItemOrder]() + FieldSerializer[ItemCreated]() + FieldSerializer[Item]()
}

case class Status(uptime: Long, startTime: Long, noOfItems: Long)

trait StatusRoutes extends HttpService with JsonProtocol {

  def statusActorSystem = actorRefFactory.asInstanceOf[ActorContext].system

  val statusRoutes =
    pathSingleSlash {
      redirect(Uri("status"), StatusCodes.MovedPermanently)
    } ~ respondWithMediaType(MediaTypes.`application/json`) {
      path("status" ~ Slash.?) {
        get {
          complete(Status(statusActorSystem.uptime, statusActorSystem.startTime, ItemService.items.size))
        }
      }
    }

}
