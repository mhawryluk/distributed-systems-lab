package homework.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{handleWebSocketMessages, parameter, pathPrefix}
import akka.stream.Materializer
import akka.http.scaladsl.server.Directives._


object Chat extends App {

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val actorMaterializer: Materializer = actorMaterializer

  private var chatRooms = Map.empty[String, Chatroom]

  def getChatroom(chatroom: String)(implicit actorSystem: ActorSystem): Chatroom = {
    chatRooms.getOrElse(chatroom, {
      val chatRoom = Chatroom(chatroom)
      chatRooms += chatroom -> chatRoom
      chatRoom
    })
  }

  val route =
    pathPrefix("chat" / Segment) { chatroom =>
      parameter("username") { username =>
        handleWebSocketMessages(Chat.getChatroom(chatroom).flow(username))
      }
    }

  val binding = Http().newServerAt("127.0.0.1", 8080).bind(route)
  println("server started")
}
