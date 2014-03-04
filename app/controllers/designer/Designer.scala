package controllers.designer

import play.api._
import java.util.Date
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import models._
import com.mongodb.casbah.Imports.ObjectId


object Designer extends Controller {
  
  def designermain = Action {
    Ok(views.html.designer.designermain(""))
  } 

  def designersearch = Action {
    Ok(views.html.designer.designersearch(""))
  }

}