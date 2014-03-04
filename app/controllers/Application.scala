package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import play.api.templates._

object Application extends Controller {

  val userForm = Form(tuple(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "confirmPassword" ->text,
      "sex" -> text,
      "tel" -> text,
      "age" -> number(min = 0, max = 100),
      "education" -> text,
      "introduce" -> text
  )
  )
 
  val infoForm = Form(tuple(
     "sex" -> text,
     "age" -> number(min = 0, max =100)
  )    
  )
  
  val userloginForm = Form(tuple(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText    
  )
  )

  def index = Action {
    Redirect(routes.Application.users)
  }

  def users = Action {
    Ok(views.html.index(userForm))
  }

  def newUser = Action { 
	  implicit request => 
	  userForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors)),
      {
          case(username,password,confirmPassword,sex,tel,age,education,introduce) => {
            if(password == confirmPassword){
	        User.create(username,password,sex,tel,age,education,introduce)
	        Redirect(routes.Application.h)
            }
            else
             Ok(Html("<p>两次输入的密码不同！</strong></p>"))
	      }
      }
    )
  }
  
  def infoChange = Action {
    Ok(views.html.infoChange(infoForm))
  }
  
  def updateUser = Action {
	  implicit request =>
	    val username = request.session.get("username").get
	    infoForm.bindFromRequest.fold(
	    errors => BadRequest(views.html.infoChange(errors)),
	    {
	      case(sex,age) => {
	        User.infoChange(username,sex,age)
	        Ok(views.html.success(username))
	      }
	    }
	    )
  }
  
  def h = Action{
    Ok(views.html.login(userloginForm))
  }
  
  def test = Action{
    val user = User.findAll.toList
    println(user)
    Ok(views.html.login(userloginForm))
  }
  
  def admin = Action{
	Ok(views.html.admin(User.all()))
  }
  
//  def info(username: String) = Action{
//    Ok(views.html.info(User.findOneByUsername(username)))
//  }

  def info = Action{implicit request =>
    Ok(views.html.info(User.findOneByUsername(request.session.get("username").get)))
  }
  
  def login = Action {
     implicit request => 
	  userloginForm.bindFromRequest.fold(
	   errors => BadRequest(views.html.login(errors)),
	   {
	     case(username,password) => {
	        if(User.check(username,password)){
	          val user_id = User.findId(username)
//	          Redirect(routes.Comment.find).withSession(request.session+("user_id" -> user_id.toString()))
	          Redirect(routes.MyPage.myPageMain).withSession(request.session+("user_id" -> user_id.toString()))
	        }
	        else if(username == "admin" && password == "admin"){
	           Redirect(routes.Application.admin)
	        }
	        else
	          Ok(Html("<p>密码错误或用户名不存在！</strong></p>"))
	      }
	   }
	  )
  }

  def deleteUser(username: String) = Action {
    User.delete(username)
    Redirect(routes.Application.users)
  }

}