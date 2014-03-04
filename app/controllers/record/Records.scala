package controllers.record

import play.api._
import java.util.Date
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import models._
import com.mongodb.casbah.Imports.ObjectId


object Records extends Controller {
  
  def recordmain = Action {
    Ok(views.html.record.recordmain(""))
  } 



}