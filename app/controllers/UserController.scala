package controllers

import javax.inject._

import play.api.mvc._
import services.UserService

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
    var bodyJson = request.body.asText
    println(s"Body json is $bodyJson")
    Ok(s"Trying to create user with json $bodyJson")
  }

  def follow(user: String, follower: String) = Action { request =>
    Ok(s"$user is trying to follow $follower")
  }

  def getFollowers(userName: String) = Action { request =>
    Ok(s"Trying to fetch all the followers of $userName")
  }
}
