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



    
case class Blogs(
    id : ObjectId = new ObjectId,
    userId: ObjectId, 
    createdTime: Date = new Date, 
    status : Int, 
    title : String, 
    blogTyp : String,
    tags : String,
    content : String)

object Blogs extends ModelCompanion[Blogs, ObjectId] {
  val dao = new SalatDAO[Blogs, ObjectId](collection = mongoCollection("blog")) {}
  
  def newBlog(userId : ObjectId, title : String, content : String, blogTyp : String,tags: String) = {
    dao.save(Blogs(userId = userId, status = 0, title = title, blogTyp = blogTyp, tags = tags, content = content))    
  }
  
  def showBlog(userId : ObjectId) = {
    val blog = dao.find(MongoDBObject("userId" -> userId, "status" -> 0)).toList
    blog
  }
  
//  var list : List[Comments] = Nil
  implicit var list = List.empty[Blogs]
  def all(id : ObjectId): List[Blogs] =   
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
//    dao.save(Blogs(userId = userId, status = 0, commentedId = commentedId, relevantUser = relevantUser, commentedType = 1, content = content))    
  }
  
  
  def delete(id : ObjectId) = {
    val blog = dao.findOneById(id).get
    dao.save(Blogs(id = id, userId = blog.userId, status = 1, title = blog.title, blogTyp = blog.blogTyp, tags = blog.tags, content = blog.content), WriteConcern.Safe)
  }
  
  def huifu(id : ObjectId, content : String, userId : ObjectId) {
    val model = dao.findOneById(id)
    
//    val model = dao.findOne(MongoDBObject("id" -> new ObjectId(id))).get
//    dao.save(Blogs(userId = userId, status = 0, commentedId = id, relevantUser = userId, commentedType = 3, content = content))
    
  }
}