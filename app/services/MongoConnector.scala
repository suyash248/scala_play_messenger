package services
import com.mongodb.casbah.Imports._

/**
  * Initializes mongo connection.
  */
object MongoConnector{
  // Initializing mongo client, other db attributes can be suplied in constructor if required.
  val connection = MongoClient()

  /**
    * Creates/gets a collection with name `collName`
    * @param dbName
    * @param collName
    * @return
    */
  def getCollection(dbName: String, collName: String) = {
    connection(dbName)(collName)
  }
}