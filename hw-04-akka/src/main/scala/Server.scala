package homework.akka

import akka.actor.typed.{ActorSystem, SpawnProtocol}
import akka.http.Version.check
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.WithTransformation
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow
import homework.akka.Server.spawnSystem

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import akka.http.scaladsl.testkit.WSProbe
import akka.http.scaladsl.testkit.WSTestRequestBuilding.WS


object Server extends App {

  def helloRoute: Route = pathEndOrSingleSlash {
    complete("Welcome to messaging service")
  }

  // create WebSocket route
  def affirmRoute: Route = path("affirm") {
    println("affirming")
    handleWebSocketMessages(
      Flow[Message].collect {
        case TextMessage.Strict(text) => TextMessage("You said " + text)
      }
    )
  }

  implicit val spawnSystem: ActorSystem[SpawnProtocol.Command] = ActorSystem(SpawnProtocol(), "spawn")

  def messageRoute: Route = pathPrefix("message" / Segment) { trainerId =>
      // await on the webflow materialization pending session actor creation by the spawnSystem
      Await.ready(ChatSessionMap.findOrCreate(trainerId).webflow(), Duration.Inf).value.get match {
        case Success(value) => handleWebSocketMessages(value)
        case Failure(exception) =>
          println(exception.getMessage)
          failWith(exception)
      }
    }


  // bind the route using HTTP to the server address and port
  val binding = Http()(spawnSystem).newServerAt("127.0.0.1", 8090).bind(messageRoute)
  println("Server running...")
}
