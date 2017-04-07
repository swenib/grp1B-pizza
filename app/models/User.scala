package models

/**
  * User entity.
  *
  * @param id   database id of the user.
  * @param name name of the user.
  */
case class User(var id: Long, var email: String, var password: String, var forename: String, var name: String, var address: String, var zipcode: Int, var city: String, var role: String, var inactive: Boolean)

object activeUser {

  var id: Long = 0
  var orderID: Long = 0
  var forename: String = _
  var name: String = _
  var address: String = _
  var zipcode: Int = _
  var city: String = _
  var role: String = "none"
  var loggedin : Boolean = false

}

case class setUser(id: Long, forename: String, name: String, address: String, zipcode: Int, city: String, role: String) {
  activeUser.id = id
  activeUser.forename = forename
  activeUser.name = name
  activeUser.address = address
  activeUser.zipcode = zipcode
  activeUser.city = city
  activeUser.role = role
}
