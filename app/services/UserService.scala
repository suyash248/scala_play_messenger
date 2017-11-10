package services

import models.user.User

class UserService {
  def test() = {
    val collection = MongoConnector.getCollection("twitter", "users")

    val userSuyash = User(userName = "suyash248", firstName = "Suyash", lastName = "Soni")
    userSuyash.insert()

  }
}
