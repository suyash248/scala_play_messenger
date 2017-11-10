package models.user

import com.mongodb.casbah.Imports.{DBObject, MongoDBObject, _}
import models.user.UserProperties._

object UserConverter {
  def convertToMongoObject(user: User): DBObject = {
    MongoDBObject(
      USER_NAME -> user.userName,
      FIRST_NAME -> user.firstName,
      LAST_NAME -> user.lastName
    )
  }

  def convertFromMongoObject(db: DBObject): User = {
    User(
      userName = db.getAsOrElse[String](USER_NAME, ""),
      firstName = db.getAsOrElse[String](FIRST_NAME, ""),
      lastName = db.getAsOrElse[String](LAST_NAME, "")
    )
  }
}