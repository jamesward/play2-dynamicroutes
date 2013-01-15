import org.apache.commons.lang3.reflect.MethodUtils
import play.api.GlobalSettings
import play.api.mvc.{Action, Handler, RequestHeader, Results}

object Global extends GlobalSettings {
  
  override def onRouteRequest(request: RequestHeader): Option[Handler] = {
    request.path match {
      case path if (path.startsWith("/resources/") && path.split('/').length > 2) => {
        
        // try to find a suitable resource controller
        val resourceName = path.split('/')(2)
        
        // there might be a cleaner way to do this with Scala Reflection
        try {
          val controllerClass = Global.getClass.getClassLoader.loadClass("controllers.resources." + resourceName)
          val methodInvocation = MethodUtils.getAccessibleMethod(controllerClass, "index").invoke(null)
          Some(methodInvocation.asInstanceOf[Handler])
        }
        catch {
          // otherwise, error
          case _: Throwable => Some(Action { request =>
            Results.NotFound("The resource for " + path + " could not be found")
          })
        }
      }
      case _ => super.onRouteRequest(request)
    }
  }
  
}
