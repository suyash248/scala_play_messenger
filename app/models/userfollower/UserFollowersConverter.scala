package models.userfollower

import com.mongodb.casbah.Imports.{DBObject, MongoDBObject, _}
import models.userfollower.UserFollowerProperties._

object UserFollowersConverter {
  def convertToMongoObject(userFollowers: UserFollowers): DBObject = {
    MongoDBObject(
      USER -> userFollowers.user,
      FOLLOWER -> userFollowers.follower
    )
  }

  def convertFromMongoObject(db: DBObject): UserFollowers = {
    UserFollowers(
      user = db.getAsOrElse[String](USER, ""),
      follower = db.getAsOrElse[String](USER, "")
    )
  }
}
