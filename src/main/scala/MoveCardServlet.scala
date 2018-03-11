import javax.servlet.http._

import db.StoryDAO
import models.Story

class MoveCardServlet extends HttpServlet {

  def handleRequest(request: HttpServletRequest) = {

    val storyNumber = request.getParameter("storyNumber")
    val toPhase = request.getParameter("phase")
    val story = StoryDAO.findByNumber(storyNumber)

    story.moveTo(toPhase) match {
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