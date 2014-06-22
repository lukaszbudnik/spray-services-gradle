package rest

import spray.routing.HttpService
import security.UserPassAuthenticator
import akka.actor.ActorContext
import spray.http.MediaTypes
import spray.routing.authentication.BasicAuth
import model.ItemOrder
import service.ItemService
import scala.concurrent.ExecutionContext.Implicits.global

trait RestRoutesV1 extends HttpService with UserPassAuthenticator with JsonProtocol {

  def actorSystemV1 = actorRefFactory.asInstanceOf[ActorContext].system

  private lazy val log = actorSystemV1.log

  val routesV1 =
    respondWithMediaType(MediaTypes.`application/json`) {
      pathPrefix("api") {
        authenticate(BasicAuth(userPassAuthenticator _, "api v1")) { user =>
          pathPrefix("v1") {
            post {
              path("item") {
                entity(as[ItemOrder]) { order =>
                  log.info(s"Got order => ${order.description}")
                  complete(ItemService.create(order))
                }
              }
            } ~
              get {
                path("item" / IntNumber) { itemRef =>
                  complete(ItemService.get(itemRef))
                }
              }
          }
        }
      }
    }
}
