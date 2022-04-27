package homework.akka

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, Sink, Source}
import akka.stream.{FlowShape, OverflowStrategy}


case class Chatroom(chatroom: String)(implicit actorSystem: ActorSystem) {

  private val chatroomActor = actorSystem.actorOf(Props[ChatroomActor])

  def flow(username: String): Flow[Message, Message, _] = {
    val source = Source.actorRef[ChatMessage](20, OverflowStrategy.fail)
    val graph = GraphDSL.createGraph(source) {
       implicit builder => sourceShape =>
        import GraphDSL.Implicits._

        val websocketReceive = builder.add(
          Flow[Message].collect {
            case TextMessage.Strict(txt) => ChatMessage(username, txt)
          }
        )

        val websocketSend = builder.add(
          Flow[ChatMessage].map {
            case ChatMessage(username, text) => TextMessage(s"$username: $text")
          }
        )

        val materialized = builder.materializedValue.map(actor => Connected(username, actor))

        val sink = Sink.actorRef[ChatEvent](chatroomActor, Disconnected(username))

        val merge = builder.add(Merge[ChatEvent](2))

        websocketReceive ~> merge.in(0)
        materialized ~> merge.in(1)
        merge ~> sink

        sourceShape ~> websocketSend

        FlowShape(websocketReceive.in, websocketSend.out)
    }
    Flow.fromGraph(graph)
  }
}
