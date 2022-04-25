package homework.akka

import akka.actor.typed.{ActorSystem, SpawnProtocol}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}


object Server extends App {
  implicit val spawnSystem: ActorSystem[SpawnProtocol.Command] = ActorSystem(SpawnProtocol(), "spawn")

  def messageRoute: Route = pathPrefix("message" / Segment) { username =>
    Await.ready(ChatSessionMap.findOrCreate(username).webflow(), Duration.Inf).value.get match {
      case Success(value) => handleWebSocketMessages(value)
      case Failure(exception) =>
        println(exception.getMessage)
        failWith(exception)
    }
  }

  // bind the route using HTTP to the server address and port
  val binding = Http()(spawnSystem).newServerAt("127.0.0.1", 8090).bind(messageRoute)
  println("server started")
}