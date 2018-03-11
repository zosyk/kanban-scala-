package models

import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}

import models.KanbanSchema.{DRIVER, JDBC_URL, PASSWORD, USERNAME}
import org.squeryl._
import org.squeryl.adapters._
import org.squeryl.PrimitiveTypeMode._


object KanbanSchema extends Schema {
  val stories = table[Story]("STORIES")


  val JDBC_URL = "jdbc:h2:tcp://localhost/~/test"
  //  val JDBC_URL = "jdbc:h2:tcp://localhost/server~/test"
  val USERNAME = "sa"
  val PASSWORD = ""
  val DRIVER = "org.h2.Driver"

  def init = {
    import org.squeryl.SessionFactory
    Class.forName(DRIVER)
    if (SessionFactory.concreteFactory.isEmpty) {
      SessionFactory.concreteFactory = Some(() =>
        Session.create(DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD), new H2Adapter))
    }
  }

  def tx[A](a: => A): A = {
    init
    inTransaction(a)
  }

  def main(args: Array[String]): Unit = {
    var conn: Connection = null
    var stmt: Statement = null

    try {
      Class.forName(DRIVER)
      conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)
      stmt = conn.createStatement
      val rs = stmt.executeQuery("select * from stories")
      while (rs.next())
        System.out.println("number: " + rs.getString("number") + ", title :" + rs.getString("title") + ", phase : " + rs.getString("phase"))
    } catch {
      case se: SQLException =>
        se.printStackTrace()
      case e: Exception =>
        e.printStackTrace()
    } finally {
      try
          if (stmt != null) conn.close()
      catch {
        case se: SQLException =>
      } // do nothing
      try
          if (conn != null) conn.close()
      catch {
        case se: SQLException =>
          se.printStackTrace()
      }
    }


    //    println("Initializing the weKanban schema")
    //    init
    //
    //    println(from(stories)(s =>  select(s)))

    //    inTransaction{drop; create}
    //
    //
    //
    //    val story = new Story("123", "test", "READY").save()
    //    println(story)
  }
}

