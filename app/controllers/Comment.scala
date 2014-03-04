package controllers

import play.api._
import java.util.Date
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import models._
import com.mongodb.casbah.Imports.ObjectId


object Comment extends Controller {
  
  var nowTime = new Date()
//  println(time)
  
//  val formFindComment = Form(tuple(
//    "cmUsername" -> nonEmptyText,   
//    "cmTime" -> ignored(nowTime),
//    "cmContent" -> nonEmptyText,
//    "cmService" -> nonEmptyText,
//    "cmAddContent" -> text
//  ))
  
  val formAddComment = Form((
//    "cmUsername" -> nonEmptyText,   
//    "cmTime" -> ignored(time),
    "content" -> nonEmptyText
//    "cmService" -> nonEmptyText,
//    "cmAddContent" -> text
  ))
  
  val formHuifuComment = Form((
//    "cmUsername" -> nonEmptyText,   
//    "cmTime" -> ignored(time),
//    "cmContent" -> nonEmptyText,
//    "cmService" -> nonEmptyText,
    "content" -> text
//    "cmAddUsername" -> text
  ))
  
//  val formFindCommnet = Form(tuple(
//    "username" -> nonEmptyText,   
//    "password" -> nonEmptyText
//  ))
  
  def find(commentedId : ObjectId) = Action {
    
    implicit request =>      
      val user_id = request.session.get("user_id").get
      val userId = new ObjectId(user_id)
      val username = User.getUsername(userId)
    clean() 
    Ok(views.html.comment(username, userId, Comments.all(commentedId)))

  }
  
  implicit def clean() = {
    Comments.list = Nil
  }
  
  def addComment(commentedId : ObjectId) = Action {
    Ok(views.html.addComment(commentedId, formAddComment))
  }
  
  def addC(commentedId : ObjectId) = Action {
    implicit request =>
      val user_id = request.session.get("user_id").get      // TODO这边需要分类。。。！！！
      formAddComment.bindFromRequest.fold(
        //处理错误
        errors => BadRequest(views.html.addComment(commentedId, errors)),
        {
//          case (cmUsername, cmTime, cmContent, cmService, cmAddContent) =>
          case (content) =>
            val userId = new ObjectId(user_id) // 这边需要用session取得用户名之类的东西
//            val time = nowTime
//            val status = 0
//            val commentedId = new ObjectId(user_id)
            val relevantUser = new ObjectId(user_id)
            
	        Comments.addComment(userId, content, commentedId, relevantUser)
	        
//            Ok(Html("评论成功"))
//	        Redirect(routes.Comment.find).withSession(request.session+("_id" -> _id))
	        Redirect(routes.Comment.find(commentedId))
        }
                
          
            
        )
  }
  
//  def complaint = Action {
//    Ok(views.html.complaint(request.session.get("_id").get))
//  }
  
  def complaint(id : ObjectId) = Action {
    Ok(Html("我要申诉的评论Id是" + id))
  }
  
  def answer(id : ObjectId, commentedId : ObjectId) = Action {
    Ok(views.html.answer(id, commentedId, formHuifuComment))
  }
  
  def delete(id : ObjectId, commentedId : ObjectId) = Action {
    Comments.delete(id)
    Redirect(routes.Comment.find(commentedId))
  }
  
  def huifu(id : ObjectId, commentedId : ObjectId) = Action {
    implicit request =>
      val user_id = request.session.get("user_id").get
      formHuifuComment.bindFromRequest.fold(
        //处理错误
        errors => BadRequest(views.html.answer(id, commentedId, errors)),
        {
//          case (cmUsername, cmTime, cmContent, cmService, cmAddContent) =>
          case (content) =>
            val username = new ObjectId(user_id)
            println("content" + content, "username" + username)
	        Comments.huifu(id, content, username)

//            Ok(Html("评论成功"))
//	        Redirect(routes.Comment.find).withSession(request.session+("_id" -> _id))
	        Redirect(routes.Comment.find(commentedId))
//	        Ok(Html("test"))
        } 
        )
  }



}