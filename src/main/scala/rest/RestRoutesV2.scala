package rest

import spray.routing.HttpService
import security.UserPassAuthenticator
import akka.actor.{PoisonPill, Props, ActorContext}
import spray.http.MediaTypes
import spray.routing.authentication.BasicAuth
import model.{Item, ItemCreated, ItemOrder}
import service.ItemService
import akka.util.Timeout
import akka.pattern.ask
import workers.ItemManager
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

trait RestRoutesV2 extends HttpService with UserPassAuthenticator with JsonProtocol {

  def actorSystemV2 = actorRefFactory.asInstanceOf[ActorContext].system

  private lazy val log = actorSystemV2.log

  val routesV2 =
    respondWithMediaType(MediaTypes.`application/json`) {
      pathPrefix("api") {
        authenticate(BasicAuth(userPassAuthenticator _, "api v2")) { user =>
          pathPrefix("v2") {
            post {
              path("item") {
                entity(as[ItemOrder]) { order =>
                  log.info(s"Got order => ${order.description}")
                  implicit val timeout = Timeout(2.seconds)
                  val manager = actorSystemV2.actorOf(Props[ItemManager])
                  val created = (manager ? order).mapTo[ItemCreated]
                  manager ! PoisonPill
                  complete(created)
                }
              }
            } ~
              get {
                path("item" / IntNumber) { itemRef =>
                  implicit val timeout = Timeout(2.seconds)
                  complete(ItemService.get(itemRef))
                  val manager = actorSystemV2.actorOf(Props[ItemManager])
                  val item = (manager ? itemRef).mapTo[Option[Item]]
                  manager ! PoisonPill
                  complete(item)
                }
              }
          }
        }
      }
    }
}
