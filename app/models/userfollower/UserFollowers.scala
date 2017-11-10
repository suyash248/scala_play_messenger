package models.userfollower

import org.bson.types.ObjectId

case class UserFollowers (_id: ObjectId, userId: ObjectId)

object UserFollowerProperties {
  val _ID = "_id"
  val USER_ID = "userId"
}
