package controllers

import javax.inject._

import com.google.gson.Gson
import play.api.mvc._
import play.api.libs.json._
import services.UserService
import models.user.User

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * users'/followers' REST endpoints.
 */
@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  /**
   * Creates a new user(if not exists)
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

  /**
    * <p>{@code PUT: users/:user/followers/:follower}</p>
    * A user {@code user} will follow another user {@code follower}
    * @param user Parent user
    * @param follower Follower of `user`
    * @return
    */
  def follow(user: String, follower: String) = Action { request =>
    val gson:Gson = new Gson()
    var res = userService.follow(user, follower)
    Ok(Json.stringify(Json.toJson(res)))
  }

  /**
    * <p>{@code GET: users/:user/followers}</p>
    *
    * Fetches paginated list of followers of given `user`.
    * If pagination attributes i.e. {@code pageNumber} & {@code perPage} are not provided as query params, all the
    * followers will be fetched at once.
    *
    * @param userName Whose follower needs to be fetched.
    * @param pageNumber
    * @param perPage
    * @return
    */
  def getFollowers(userName: String, pageNumber: Int, perPage: Int) = Action { request =>
    var res = userService.getFollowers(userName, pageNumber, perPage)
    Ok(Json.stringify(Json.toJson(res)))
  }
}
