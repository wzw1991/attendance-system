package controllers

import play.api._
import java.util.Date
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import models._

object Comment extends Controller {
  
  var time = new Date().toLocaleString()
  println(time)
  
  val formAddComment = Form(tuple(
    "cmUsername" -> nonEmptyText,   
    "cmTime" -> ignored(time),
    "cmContent" -> nonEmptyText,
    "cmService" -> nonEmptyText,
    "cmAddContent" -> text
  ))
  
//  val formFindCommnet = Form(tuple(
//    "username" -> nonEmptyText,   
//    "password" -> nonEmptyText
//  ))
  
  def find = Action {
    println("DB.all()   " + DB.all())
    Ok(views.html.comment(DB.all()))
  }
  
  def addComment = Action {
    Ok(views.html.addComment(formAddComment))
  }
  
  def addC = Action {
    implicit request =>
      formAddComment.bindFromRequest.fold(
        //处理错误
        errors => BadRequest(views.html.addComment(errors)),
        {
          case (cmUsername, cmTime, cmContent, cmService, cmAddContent) =>
            
	        DB.addComment(cmUsername, cmTime, cmContent, cmService, cmAddContent)

//            Ok(Html("评论成功"))
//	        Redirect(routes.Comment.find).withSession(request.session+("_id" -> _id))
	        Redirect(routes.Comment.find)
        }
                
          
            
        )
  }
  
//  def complaint = Action {
//    Ok(views.html.complaint(request.session.get("_id").get))
//  }
  
  def complaint = TODO



}