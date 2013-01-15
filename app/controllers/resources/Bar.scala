package controllers.resources

import play.api.mvc.{Action, Controller}

object Bar extends Controller {
  
  def index = Action {
    Ok(views.html.resources.Bar.index())
  }

}
