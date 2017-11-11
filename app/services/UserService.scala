package services

import com.mongodb.DBObject
import models.user.User
import models.userfollower.UserFollowers.{collection, isFollowing}
import models.userfollower.{UserFollowers, UserFollowersConverter}

class UserService {

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

  def getFollowers(user: String): Map[String, Any] = {
    val userFollowersBson = UserFollowers.getFollowers(user)
    Map("status"-> "success",
      "message"-> s"Total",
      "followers"-> userFollowersBson)
  }

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
