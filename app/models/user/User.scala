package models.user

import com.mongodb.casbah.Imports.{DBObject, MongoDBObject}
import org.bson.types.ObjectId
import services.MongoConnector

/**
  * Model/entity representing a legitimate user.
  * @param userName
  * @param firstName
  * @param lastName
  */
case class User(userName: String, firstName: String, lastName: String)

object User {
  val (dbName, collName) = UserProperties.getDBAndCollectionName()
  val collection = MongoConnector.getCollection(dbName = dbName, collName = collName)

  /**
    * Persists user to database.
    * @param user
    * @return
    */
  def save(user: User) = collection.insert(UserConverter.convertToMongoObject(user))

  def findById(id: ObjectId) = {
    val query = MongoDBObject(UserProperties._ID -> id)
    convertFromMongo(collection.findOne(query))
  }

  /**
    * Checks if a user with `userName` is already present.
    * @param userName
    * @return
    */
  def isExist(userName: String): Boolean = findByUserName(userName) != None

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
  /**
    * userName must be unique for each user.
    */
  val USER_NAME = "userName"
  val FIRST_NAME = "firstName"
  val LAST_NAME = "lastName"

  /**
    * Returns the mongo database and collection name corresponding to this model/entity.
    * @return
    */
  def getDBAndCollectionName() = ("twitter", "users")
}
