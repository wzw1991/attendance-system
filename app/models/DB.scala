package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import mongoContext._
import models._


//case class DB() extends App 
//
//object DB {
//  val mongoConn = MongoConnection()
//  val mongoColl = mongoConn("test")("Comment")
//  
//  def addComment(cmUsername : String, cmTime : String, cmContent : String, cmService : String) {  
//    val bread1 = MongoDBObject("cmUsername" -> cmUsername,
//      "cmTime" -> cmTime, "cmContent" -> cmContent, "cmService" -> cmService)
//    mongoColl += bread1
//  }
//  
//  def huifuComment(cmUsername : String, cmTime : String, cmContent : String, cmService : String, cmAddContent : String) {  
//    val bread1 = MongoDBObject("cmUsername" -> cmUsername,
//      "cmTime" -> cmTime, "cmContent" -> cmContent, "cmService" -> cmService, "cmAddContent" -> cmAddContent)
//    mongoColl += bread1
//  }
//  
//  def all() : List[Comment_m] = {
////    println("mongoColl   " + mongoColl)
////    println("mongoColl.find   " + mongoColl.find.next.toString())
////    println("mongoColl.findOne   " + mongoColl.findOne)
//    val all = mongoColl.find()
////    val c = all.toList
////    println("all   " + all.toString)
//    var list : List[Comment_m] = Nil
////    type name = new type(arguments);
//    while(all.hasNext){
//      val b = all.next
//      println("b  " + List(b))
////      b ::: list
//  	  List(b) ::: list
//      println("list  " + list)
//      
//    }
//    list
//
//  }
//}

//case class Comment_m(cmUsername: String, cmTime: String, cmContent : String, cmService : String, cmAddContent : String)

case class Comment_m(
    id : ObjectId = new ObjectId,
    cmUsername: String, 
    cmTime: String, 
    cmContent : String, 
    cmService : String, 
    cmAddContent : String, 
    cmAddUsername : String)
    
//case class Comments(
//    id : ObjectId = new ObjectId,
//    userId: ObjectId, 
//    time: Date, 
//    status : String, 
//    refCommentId: ObjectId, 
//    commentId : ObjectId, 
//    commentType : String)

object DB extends ModelCompanion[Comment_m, ObjectId] {
  val dao = new SalatDAO[Comment_m, ObjectId](collection = mongoCollection("Comment")) {}
  
  def all(): List[Comment_m] = 
    {
      dao.find(MongoDBObject.empty).toList
      
    
    }
  
//  def addComment(cmUsername : String, cmTime : String, cmContent : String, cmService : String, cmAddContent : String) {
//    dao.insert(Comment_m(cmUsername = cmUsername,cmTime = cmTime,cmContent = cmContent,cmService = cmService, cmAddContent = cmAddContent))
//  }
  
  def addComment(cmUsername : String, cmTime : String, cmContent : String, cmService : String) = {
    dao.save(Comment_m(cmUsername = cmUsername,cmTime = cmTime,cmContent = cmContent,cmService = cmService, cmAddContent = "", cmAddUsername = ""))
    
//    dao.ids(MongoDBObject.empty)
  }
  
  def huifu(id : String, cmAddContent : String, cmAddUsername : String) {
    val model = dao.findOneById(new ObjectId(id))
    
//    val model = dao.findOne(MongoDBObject("id" -> new ObjectId(id))).get
    dao.update(MongoDBObject("id" -> new ObjectId(id)), MongoDBObject("cmUsername" -> model.get.cmUsername, "cmTime" -> model.get.cmTime,"cmContent" -> model.get.cmContent,"cmService" -> model.get.cmService, "cmAddContent" -> cmAddContent, "cmAddUsername" -> cmAddUsername))
    
  }
}