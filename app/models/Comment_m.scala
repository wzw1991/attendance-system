package models
import play.api._
import play.api.data._
import play.api.data.Forms._

//case class Comment_m(cmId : Long, cmUsername: String, cmTime: String, cmContent : String, cmService : String, cmAddContent : String)
case class Comment_m(cmUsername: String, cmTime: String, cmContent : String, cmService : String, cmAddContent : String)