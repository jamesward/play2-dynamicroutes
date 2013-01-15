package controllers.resources

import play.api.mvc.{Action, Controller}

object Foo extends Controller {

  def index = Action {
    Ok(views.html.resources.Foo.index())
  }

}