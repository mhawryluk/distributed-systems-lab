package homework.akka

import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream._

object Main extends App {

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val actorMaterializer: Materializer = actorMaterializer

  val route =
    pathPrefix("chat" / Segment) { chatroom =>
      parameter("username") { username =>
        handleWebSocketMessages(Chat.getChatroom(chatroom).flow(username))
      }
    }

  val binding = Http().newServerAt("127.0.0.1", 8080).bind(route)
  println("server started")
}
