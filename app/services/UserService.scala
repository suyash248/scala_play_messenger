package services

import models.user.User
import models.userfollower.UserFollowers.{collection, isFollowing}
import models.userfollower.{UserFollowers, UserFollowersConverter}
import scala.collection.mutable.ListBuffer

class UserService {

  /**
    * For `user` to follow another user (`follower`)
    * @param user User to be followed
    * @param follower `user`'s follower
    * @return
    */
  def follow(user: String, follower: String): Map[String, String] = {
    var res: Map[String, String] = Map()
    if (User.isExist(user) && User.isExist(follower)) {
      val isAlreadyFollowing:Boolean = isFollowing(user = user, follower = follower)
      if (!isAlreadyFollowing) {
        collection.insert(UserFollowersConverter.convertToMongoObject(UserFollowers(user, follower)))
        res += "status" -> "success"
        res += "message" -> s"$user started following $follower"
      } else {
        res += "status" -> "warning"
        res += "message" -> s"$user is already following $follower"
      }
    } else {
      res += "status" -> "error"
      res += "message" -> s"Either $user or $follower doesn't exist"
    }
    res
  }

  /**
    * Fetches paginated list of `user`'s followers.
    * @param user User whose followers to be fetched.
    * @param pageNumber current page number, starts from 1.
    * @param perPage number of followers to be shown per page.
    * @return list of followers
    */
  def getFollowers(user: String, pageNumber: Int, perPage: Int): List[Map[String, String]] = {
    var followers: ListBuffer[Map[String, String]] = ListBuffer()
    var userFollowers = UserFollowers.getFollowers(user, pageNumber, perPage).foreach(mongoObj =>
      followers += Map("user"-> mongoObj.get("user").toString,
        "follower"-> mongoObj.get("follower").toString)
    )
    followers.toList
  }

  /**
    * Creates a new user if there is no such user exists with tlhe same userName.
    * @param userName
    * @param firstName
    * @param lastName
    * @return
    */
  def createUser(userName: String, firstName: String, lastName: String): Map[String, String] = {
    var res: Map[String, String] = Map()
    if (User.isExist(userName)) {
      res += "status" -> "warning"
      res += "message" -> s"User with username $userName already exists"
    } else {
      User.save(User(userName, firstName, lastName))
      res += "status" -> "success"
      res += "message" -> s"User with username $userName created successfully"
    }
    res
  }
}
