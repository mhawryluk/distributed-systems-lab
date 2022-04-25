package homework.akka
import CoreChatEvents._
import WebSocketsEvents._

import akka.actor.typed.SpawnProtocol.Spawn
import akka.actor.typed._
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl._
import akka.stream.typed.scaladsl.{ActorSink, ActorSource}
import akka.stream.{FlowShape, OverflowStrategy}
import akka.util.Timeout

import scala.concurrent.{ExecutionContextExecutor, Future}



class ChatSession(username: String)(implicit system: ActorSystem[SpawnProtocol.Command]) {
  import ChatSession._

  import akka.actor.typed.scaladsl.AskPattern._

  import scala.concurrent.duration._

  implicit val timeout: Timeout = Timeout(3.seconds)
  implicit val ec: ExecutionContextExecutor = system.dispatchers.lookup(DispatcherSelector.default())

  private[this] val sessionActor: Future[ActorRef[CoreChatEvent]] =
    system.ask[ActorRef[CoreChatEvent]] { ref =>
      Spawn[CoreChatEvent](
        behavior = SessionActor.receive(None),
        name = s"$username-chat-session",
        props = Props.empty,
        replyTo = ref
      )
    }

  def webflow(): Future[Flow[Message, Message, _]]  = sessionActor.map { session =>
    Flow.fromGraph(
      GraphDSL.create(webSocketsActor) { implicit builder => socket =>

        import GraphDSL.Implicits._

        val webSocketSource = builder.add(
          Flow[Message].collect {
            case TextMessage.Strict(text) =>
              UserMessage(username, text)
          }
        )

        val webSocketSink = builder.add(
          Flow[WebSocketsEvent].collect {
            case MessageToUser(from, text) =>
              TextMessage(from + ": " + text)
          }
        )

        val routeToSession = builder.add(ActorSink.actorRef[CoreChatEvent](
          ref = session,
          onCompleteMessage = Disconnected,
          onFailureMessage = Failed.apply
        ))

        val materializedActorSource = builder.materializedValue.map(ref => Connected(ref))


        val merge = builder.add(Merge[CoreChatEvent](2))

        webSocketSource ~> merge.in(0)
        materializedActorSource ~> merge.in(1)
        merge ~> routeToSession
        socket ~> webSocketSink

        FlowShape(webSocketSource.in, webSocketSink.out)
      }
    )
  }
}

object ChatSession {
  def apply(username: String)(implicit system: ActorSystem[SpawnProtocol.Command]): ChatSession = new ChatSession(username)

  val webSocketsActor: Source[WebSocketsEvent, ActorRef[WebSocketsEvent]] = ActorSource.actorRef[WebSocketsEvent](
    completionMatcher = {
      case Complete =>
    },
    failureMatcher = {
      case WebSocketsEvents.Failure(ex) => throw ex
    },
    bufferSize = 5,
    OverflowStrategy.fail)
}
