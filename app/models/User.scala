package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import mongoContext._

case class User(
                 id: ObjectId = new ObjectId,
                 username: String,
                 password: String,
                 sex: String,
                 tel: String,
                 age: Int,
                 education:String,
                 introduce: String
                 )

object User extends ModelCompanion[User, ObjectId] {
  val dao = new SalatDAO[User, ObjectId](collection = mongoCollection("user")) {}

  def all(): List[User] = dao.find(MongoDBObject.empty).toList
  
  def findId(username : String) = 
    {
    val p = dao.find(MongoDBObject("username" -> username))
    val id = p.next.id
    id
    }
  
  def getUsername(id : Object) = 
    {
    val p = dao.find(MongoDBObject("_id" -> id))
    val username = p.next.username
    username
    }
  
  def create(username: String, password: String,sex:String,tel: String, age: Int,education:String,introduce:String) {
    dao.insert(User(username = username,password = password,sex = sex,tel = tel,age = age,education = education,introduce = introduce))
  }

  def delete(username: String) {
    dao.remove(MongoDBObject("username" -> username))
  }
  
  def findOneByUsername(username: String): Option[User] = dao.findOne(MongoDBObject("username" -> username))
  
  def check(username: String,password: String) : Boolean = {
    dao.find(MongoDBObject("username" -> username, "password" ->password)).hasNext
  }
  
  def infoChange(username: String,sex: String, age: Int){
    val model = dao.findOne(MongoDBObject("username" -> username)).get
//    dao.update(MongoDBObject("username" -> username),{$set:{sex:sex}},{$set:{age:age}})
   dao.update(MongoDBObject("username" -> username),MongoDBObject("username" -> model.username,"password" -> model.password,"tel" ->model.tel,"education" ->model.education,"introduce" ->model.introduce,"sex" -> sex,"age" ->age))
  }
}