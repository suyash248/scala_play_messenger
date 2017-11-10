package models.userfollower

import com.mongodb.casbah.Imports.{DBObject, MongoDBObject, _}
import models.userfollower.UserFollowerProperties._

object UserFollowersConverter {
  def convertToMongoObject(userFollowers: UserFollowers): DBObject = {
    MongoDBObject(
      _ID -> userFollowers._id,
      USER_ID -> userFollowers.userId
    )
  }

  def convertFromMongoObject(db: DBObject): UserFollowers = {
    UserFollowers(
      _id = db.getAsOrElse[ObjectId](_ID, null),
      userId = db.getAsOrElse[ObjectId](USER_ID, null)
    )
  }
}
