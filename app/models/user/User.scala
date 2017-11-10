package models.user

import com.mongodb.casbah.Imports.{DBObject, MongoDBObject}
import org.bson.types.ObjectId
import services.MongoConnector

case class User(userName: String, firstName: String, lastName: String) {

  val (dbName, collName) = UserProperties.getDBAncCollectionName()
  val collection = MongoConnector.getCollection(dbName = dbName, collName = collName)
  def insert() = {
    collection.insert(UserConverter.convertToMongoObject(this))
  }

  def findOne(id: ObjectId) = {
    convertFromMongo(collection.findOne(MongoDBObject(UserProperties._ID -> id)))
  }

  def findByUserName(userName :String) = {
    val query = MongoDBObject(UserProperties.USER_NAME -> userName)
    convertFromMongo(collection.findOne(query))
  }

  private def convertFromMongo(dbObj : Option[DBObject]) : Option[User]= {
    dbObj match {
      case Some(userDoc) => Some(UserConverter.convertFromMongoObject(userDoc))
      case _ => None
    }
  }
}

object UserProperties {
  val _ID = "_id"
  val USER_NAME = "userName"
  val FIRST_NAME = "firstName"
  val LAST_NAME = "lastName"

  def getDBAncCollectionName() = ("twitter", "users")
}
