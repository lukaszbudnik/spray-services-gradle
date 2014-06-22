package rest

import spray.testkit.Specs2RouteTest
import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import spray.http.{ContentTypes, BasicHttpCredentials, StatusCodes}
import org.specs2.runner.JUnitRunner
import model.{ItemCreated, ItemOrder, Item}
import service.ItemService

@RunWith(classOf[JUnitRunner])
class RestRoutesV2Spec extends Specification with Specs2RouteTest with JsonProtocol {

  val restRoutes = new RestRoutesV2 {
    // return the Spec2RouteTest system
    override def actorSystemV2 = system

    override def actorRefFactory = system
  }

  // add a test item to items
  ItemService.items = Map(1 -> Item(1, "test item"))

  val credentials = BasicHttpCredentials("lukasz", "budnik")

  "The service V2" should {

    "return JSON with item" in {
      Get("/api/v2/item/1") ~> addCredentials(credentials) ~> restRoutes.routesV2 ~> check {
        handled === true
        status === StatusCodes.OK
        body.contentType === ContentTypes.`application/json`
        responseAs[Option[Item]] must beSome.which {
          case Item(ref, description) if ref == 1 && description == "test item" => true
        }
      }
    }

    "create new item from POST JSON with item order and reply with item created message" in {
      Post("/api/v2/item", ItemOrder("v1 test item order")) ~> addCredentials(credentials) ~> restRoutes.routesV2 ~> check {
        handled === true
        status === StatusCodes.OK
        body.contentType === ContentTypes.`application/json`
        responseAs[ItemCreated].reference > 0
      }
    }
  }

}
