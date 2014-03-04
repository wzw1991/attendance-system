package controllers

import play.api._
import java.util.Date
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import models._
import com.mongodb.casbah.Imports.ObjectId


object Blog extends Controller {
  
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
  
//    val formBlog = Form(mapping(
//    "title" -> nonEmptyText,   
//    "content" -> nonEmptyText,
//    "blogTyp" -> nonEmptyText,
//    "tags" -> nonEmptyText
//  ){(title, content, blogTyp, tags) => Blogs(new ObjectId, username, password._1, sex, age, tel, email, education, introduce, new Date(), new Date())}
//    { user => Some(user.username, (user.password, ""), user.sex, user.age, user.tel, user.email, user.education, user.introduce, false)}
//  
//    )
    
  val formBlog = Form(tuple(
    "title" -> nonEmptyText,   
    "content" -> nonEmptyText,
    "blogTyp" -> nonEmptyText,
    "tags" -> nonEmptyText
  ))
  
  def find = Action {
    
    implicit request =>      
      val user_id = request.session.get("user_id").get
      val userId = new ObjectId(user_id)
      val username = User.getUsername(userId)
    clean() 
    Ok(views.html.comment(username, userId, Comments.all(userId)))

  }
  
  def newBlog(id : ObjectId) = Action {
    Ok(views.html.blog.blog(id, formBlog))
  }
  
  def deleteBlog(id : ObjectId) = Action {
     implicit request =>
      val user_id = request.session.get("user_id").get
      val userId = new ObjectId(user_id)
    Blogs.delete(id)
    Redirect(routes.Blog.showBlog(userId))
  }
  
//  def test = Action  {
//    
//    implicit request =>      
//      val user_id = request.session.get("user_id").get
//      val userId = new ObjectId(user_id)
//      val username = User.getUsername(userId)
//   Redirect(routes.Blog.showBlog(userId))
//
//
//  }
  
  def showBlog(id : ObjectId) = Action {
    val list = Blogs.showBlog(id)
    Ok(views.html.blog.findBlogs(list))


  }
  
  def writeBlog(id : ObjectId) = Action {
      implicit request =>
      formBlog.bindFromRequest.fold(
        //处理错误
        errors => BadRequest(views.html.blog.blog(id, errors)),
        {
          case (title,content,blogTyp,tags) =>
//            val userId = new ObjectId(user_id) // 这边需要用session取得用户名之类的东西
//            val commentedId = new ObjectId(user_id)
//            val relevantUser = new ObjectId(user_id)            
	        Blogs.newBlog(id, title,content,blogTyp,tags)
	        Ok(views.html.blog.showBlog(id))
        }
                
          
            
        )
  }
  
  implicit def clean() = {
    Comments.list = Nil
  }
  
//  def addComment = Action {
//    Ok(views.html.addComment(formAddComment))
//  }
  
//  def addC = Action {
//    implicit request =>
//      val user_id = request.session.get("user_id").get
//      formAddComment.bindFromRequest.fold(
//        //处理错误
//        errors => BadRequest(views.html.addComment(errors)),
//        {
////          case (cmUsername, cmTime, cmContent, cmService, cmAddContent) =>
//          case (content) =>
//            val userId = new ObjectId(user_id) // 这边需要用session取得用户名之类的东西
////            val time = nowTime
////            val status = 0
//            val commentedId = new ObjectId(user_id)
//            val relevantUser = new ObjectId(user_id)
//            
//	        Comments.addComment(userId, content, commentedId, relevantUser)
//	        
////            Ok(Html("评论成功"))
////	        Redirect(routes.Comment.find).withSession(request.session+("_id" -> _id))
//	        Redirect(routes.Comment.find)
//        }
//                
//          
//            
//        )
//  }
  
//  def complaint = Action {
//    Ok(views.html.complaint(request.session.get("_id").get))
//  }
  
  def complaint(id : ObjectId) = Action {
    Ok(Html("我要申诉的评论Id是" + id))
  }
  
//  def answer(id : ObjectId) = Action {
//    Ok(views.html.answer(id, formHuifuComment))
//  }
  
//  def delete(id : ObjectId) = Action {
//    Comments.delete(id)
//    Redirect(routes.Comment.find)
//  }
  
//  def huifu(id : ObjectId) = Action {
//    implicit request =>
//      val user_id = request.session.get("user_id").get
//      formHuifuComment.bindFromRequest.fold(
//        //处理错误
//        errors => BadRequest(views.html.answer(id, errors)),
//        {
////          case (cmUsername, cmTime, cmContent, cmService, cmAddContent) =>
//          case (content) =>
//            val username = new ObjectId(user_id)
//            println("content" + content, "username" + username)
//	        Comments.huifu(id, content, username)
//
////            Ok(Html("评论成功"))
////	        Redirect(routes.Comment.find).withSession(request.session+("_id" -> _id))
//	        Redirect(routes.Comment.find)
////	        Ok(Html("test"))
//        } 
//        )
//  }



}