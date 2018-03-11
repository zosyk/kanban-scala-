import javax.servlet.http._

import views.CreateStory

class HomePageServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    response.setContentType("text/html")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(s"""${CreateStory()}""")
  }

}