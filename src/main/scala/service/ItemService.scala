package service

import model.{ItemCreated, ItemOrder, Item}
import scala.util.Random
import java.util.concurrent.atomic.AtomicInteger

object ItemService {

  var items = Map[Int, Item]()

  val counter = new AtomicInteger()

  private val random = new Random()

  def simulateErrors[A](block: => A): A = {
    val i = counter.incrementAndGet

    if (i > 10) {
      //sys.error("too much load")
    }

    val result = block
    counter.decrementAndGet
    result
  }

  def create(itemOrder: ItemOrder): ItemCreated = {
    simulateErrors {
      Thread.sleep(250 + random.nextInt(100))
      val ref = random.nextInt(Int.MaxValue)
      items = items + ((ref, Item(ref, itemOrder.description)))
      ItemCreated(ref)
    }
  }

  def get(ref: Int): Option[Item] = {
    simulateErrors {
      Thread.sleep(150 + random.nextInt(100))
      items.get(ref)
    }
  }


}
