package security

import spray.routing.authentication.UserPass
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

trait UserPassAuthenticator {

  def userPassAuthenticator(userPass: Option[UserPass]): Future[Option[String]] = Future {
    if (userPass.exists(up => up.user == "lukasz" && up.pass == "budnik")) Some("lukaszbudnik")
    else None
  }
}
