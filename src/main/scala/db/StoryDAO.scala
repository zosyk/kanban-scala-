package db

import java.sql.{Connection, DriverManager, Statement}

import models.Story

object StoryDAO {
  def updatePhaseByNumber(number: String, phase: String) = {
    init
    val stmt = conn.createStatement()
    stmt.executeUpdate(s"update stories set phase = '$phase' where number = '$number'")
  }

  def findByNumber(number: String): Story = {
    init
    val statement = conn.createStatement()
    val rs = statement.executeQuery(s"select * from stories where number = '$number'")

    if(rs.next())
      new Story(rs.getString("number"), rs.getString("title"), rs.getString("phase"))
    else
      null
  }


  val JDBC_URL = "jdbc:h2:tcp://localhost/~/test"
  val USERNAME = "sa"
  val PASSWORD = ""
  val DRIVER = "org.h2.Driver"

  var conn: Connection = null

  def init: Unit = {
    if (conn != null) return

    Class.forName(DRIVER)
    conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)
  }

  def save(story: Story) = {
    init
    val statement = conn.createStatement()
    statement.executeUpdate(s"insert into stories(number, title, phase) values('${story.number}', '${story.title}', '${story.phase}')")
  }

  def findAllByPhase(phase: String): List[Story] = {
    init
    val statement = conn.createStatement()
    val rs = statement.executeQuery(s"select * from stories where phase = '$phase'")
    var result: List[Story] = List()

    while (rs.next())
      result = new Story(rs.getString("number"), rs.getString("title"), rs.getString("phase")) :: result

    result
  }

  def main(args: Array[String]): Unit = {
    init

    var stmt: Statement = null
    stmt = conn.createStatement
    val rs = stmt.executeQuery("select * from stories")
    while (rs.next())
      System.out.println("number: " + rs.getString("number") + ", title :" + rs.getString("title") + ", phase : " + rs.getString("phase"))
  }

}
