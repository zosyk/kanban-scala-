import javax.servlet.http._

import views.KanbanBoard

class KanbanBoardServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    response.setContentType("text/html")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(s"""${KanbanBoard()}""")
  }

}