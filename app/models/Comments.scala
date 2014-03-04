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



    
case class Comments(
    id : ObjectId = new ObjectId,
    userId: ObjectId, 
    time: Date = new Date, 
    status : Int, 
//    refCommentId: ObjectId, 
    commentedId : ObjectId, 
    relevantUser : ObjectId,
    commentedType : Int,
    content : String)

object Comments extends ModelCompanion[Comments, ObjectId] {
  val dao = new SalatDAO[Comments, ObjectId](collection = mongoCollection("CommentDemo")) {}
  
//  var list : List[Comments] = Nil
  implicit var list = List.empty[Comments]
  def all(id : ObjectId): List[Comments] =   
    {
//    val i = Comments.list.distinct
     val l = dao.find(MongoDBObject("commentedId" -> id, "status" -> 0)).toList
     if (!l.isEmpty){
     l.foreach(
       {
           r =>list :::= List(r)
           all(r.id)
       })
     }
     list
//     i
     
    
    }
  
  
//  def addComment(cmUsername : String, cmTime : String, cmContent : String, cmService : String, cmAddContent : String) {
//    dao.insert(Comment_m(cmUsername = cmUsername,cmTime = cmTime,cmContent = cmContent,cmService = cmService, cmAddContent = cmAddContent))
//  }
  
  def addComment(userId : ObjectId, content : String, commentedId : ObjectId, relevantUser : ObjectId) = {
    dao.save(Comments(userId = userId, status = 0, commentedId = commentedId, relevantUser = relevantUser, commentedType = 1, content = content))    
  }
  
  
  def delete(id : ObjectId) = {
    val comment = dao.findOneById(id).get
    dao.save(Comments(id = id, userId = comment.userId, status = 1, commentedId = comment.commentedId, relevantUser = comment.relevantUser, commentedType = comment.commentedType, content = comment.content), WriteConcern.Safe)
  }
  
  def huifu(id : ObjectId, content : String, userId : ObjectId) {
    val model = dao.findOneById(id)
    
//    val model = dao.findOne(MongoDBObject("id" -> new ObjectId(id))).get
    dao.save(Comments(userId = userId, status = 0, commentedId = id, relevantUser = userId, commentedType = 3, content = content))
    
  }
}