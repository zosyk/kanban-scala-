import javax.servlet.http._

import views.CreateStory
import models.Story

class CreateCardServlet extends HttpServlet {

  def handleRequest(request: HttpServletRequest) = {

    val storyNumber = request.getParameter("storyNumber")
    val title = request.getParameter("title")

    val story = new Story(storyNumber, title, "ready")

    story.save() match {
      case Right(message) => message
      case Left(error) => error
    }
  }


  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    val result = handleRequest(request)

    response.setContentType("text/html")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(s"""$result""")
  }

}