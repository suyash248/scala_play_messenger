package controllers

import javax.inject._

import com.google.gson.Gson
import play.api.mvc._
import play.api.libs.json._
import services.UserService
import models.user.User

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def createUser = Action { request =>
    val body = request.body
    if (body == None || body.asJson == None) {
      Forbidden(Json.stringify(Json.toJson(Map("status"-> "error", "message"-> "Invalid request body"))))
    }
    implicit val fooFormat: Format[User] = Json.format[User]
    val user: User = body.asJson.get.as[User]
    val res = userService.createUser(user.userName, user.firstName, user.lastName)
    Ok(Json.stringify(Json.toJson(res)))
  }

  def follow(user: String, follower: String) = Action { request =>
    val gson:Gson = new Gson()
    var res = userService.follow(user, follower)
    Ok(Json.stringify(Json.toJson(res)))
  }

  def getFollowers(userName: String, pageNumber: Int, perPage: Int) = Action { request =>
    var res = userService.getFollowers(userName, pageNumber, perPage)
    Ok(Json.stringify(Json.toJson(res)))
  }
}
