package workers

import akka.actor.{ActorLogging, Actor}
import model.ItemOrder
import service.ItemService
import scala.concurrent.blocking

class ItemManager extends Actor with ActorLogging {

  def receive = _ match {

    case order: ItemOrder => blocking {
      log.info(s"Got order => ${order.description}")
      sender ! ItemService.create(order)
    }

    case itemRef: Int => blocking {
      sender ! ItemService.get(itemRef)
    }

  }

}
