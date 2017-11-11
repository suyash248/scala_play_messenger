package controllers

import javax.inject._
import play.api.mvc._
import services.UserService

@Singleton
class HomeController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
