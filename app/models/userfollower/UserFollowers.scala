package models.userfollower

import com.mongodb.casbah.Imports.{DBObject, MongoDBObject}
import org.bson.types.ObjectId
import services.MongoConnector

/**
  * Represents User-Follower mapping.
  * @param user
  * @param follower
  */
case class UserFollowers (user: String, follower: String)

object UserFollowers {
  val (dbName, collName) = UserFollowerProperties.getDBAndCollectionName()
  val collection = MongoConnector.getCollection(dbName = dbName, collName = collName)

  /**
    * Persists {@link models.userFollower.UserFollowers} in database.
    * @param user Parent user's username.
    * @param follower Parent <b>user</b>'s follower
    * @return
    */
  def save(user: String, follower: String) = collection.insert(UserFollowersConverter.convertToMongoObject(UserFollowers(user, follower)))

  def findById(id: ObjectId) = {
    val query = MongoDBObject(UserFollowerProperties._ID -> id)
    convertFromMongo(collection.findOne(query))
  }

  /**
    * Checks if `user` is already following `follower`
    * @param user
    * @param follower
    * @return
    */
  def isFollowing(user: String, follower: String) = {
    val query = MongoDBObject(UserFollowerProperties.USER-> user, UserFollowerProperties.FOLLOWER -> follower)
    convertFromMongo(collection.findOne(query)) != None
  }

  /**
    * Fetches paginated list of `user`'s follower(s).
    * @param user
    * @param pageNumber
    * @param perPage
    * @return
    */
  def getFollowers(user :String, pageNumber: Int, perPage: Int) = {
    val query = MongoDBObject(UserFollowerProperties.USER -> user)
    if (pageNumber <=0 || perPage <=0 ) {
      collection.find(query)
    } else {
      val followers = collection.find(query).skip((pageNumber - 1) * perPage).limit(perPage).toList
      followers
    }
  }

  /**
    * Converts mongo supported BSON doc to scala object.
    * @param dbObj
    * @return
    */
  def convertFromMongo(dbObj : Option[DBObject]) : Option[UserFollowers]= {
    dbObj match {
      case Some(userDoc) => Some(UserFollowersConverter.convertFromMongoObject(userDoc))
      case _ => None
    }
  }
}

/**
  * UserFollowers' attributes.
  */
object UserFollowerProperties {
  val _ID = "_id"
  val USER = "user"
  val FOLLOWER = "follower"

  /**
    * Returns the mongo database and collection name corresponding to this model/entity.
    * @return
    */
  def getDBAndCollectionName() = ("twitter", "userFollowers")
}
