package services
import com.mongodb.casbah.Imports._

object MongoConnector{
  val connection = MongoClient()
  print(s"Created mongo connection $connection")

  def getCollection(dbName: String, collName: String) = {
    connection(dbName)(collName)
  }
}