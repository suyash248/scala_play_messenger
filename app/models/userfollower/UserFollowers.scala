package models.userfollower

import com.mongodb.casbah.Imports.{DBObject, MongoDBObject}
import models.user.User
import org.bson.types.ObjectId
import services.MongoConnector

case class UserFollowers (user: String, follower: String)

object UserFollowers {
  val (dbName, collName) = UserFollowerProperties.getDBAndCollectionName()
  val collection = MongoConnector.getCollection(dbName = dbName, collName = collName)

  def save(user: String, follower: String) = collection.insert(UserFollowersConverter.convertToMongoObject(UserFollowers(user, follower)))

  def findById(id: ObjectId) = {
    val query = MongoDBObject(UserFollowerProperties._ID -> id)
    convertFromMongo(collection.findOne(query))
  }

  def isFollowing(user: String, follower: String) = {
    val query = MongoDBObject(UserFollowerProperties.USER-> user, UserFollowerProperties.FOLLOWER -> follower)
    convertFromMongo(collection.findOne(query)) != None
  }

  def getFollowers(user :String, pageNumber: Int, perPage: Int) = {
    val query = MongoDBObject(UserFollowerProperties.USER -> user)
    if (pageNumber <=0 || perPage <=0 ) {
      collection.find(query)
    } else {
      val followers = collection.find(query).skip((pageNumber - 1) * perPage).limit(perPage).toList
      followers
    }
  }

  def convertFromMongo(dbObj : Option[DBObject]) : Option[UserFollowers]= {
    dbObj match {
      case Some(userDoc) => Some(UserFollowersConverter.convertFromMongoObject(userDoc))
      case _ => None
    }
  }
}

object UserFollowerProperties {
  val _ID = "_id"
  val USER = "user"
  val FOLLOWER = "follower"

  def getDBAndCollectionName() = ("twitter", "userFollowers")
}
