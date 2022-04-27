package homework.akka

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, Sink, Source}
import akka.stream.{FlowShape, OverflowStrategy}


case class Chatroom(roomName: String)(implicit actorSystem: ActorSystem) {

  private val chatroomActor = actorSystem.actorOf(Props(classOf[ChatroomActor]))

  def flow(username: String): Flow[Message, Message, _] = {

    Flow.fromGraph(GraphDSL.createGraph(Source.actorRef[ChatMessage](10, OverflowStrategy.fail)) {

      import GraphDSL.Implicits._

      implicit builder => source =>

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

          source ~> websocketSend

          FlowShape(websocketReceive.in, websocketSend.out)
    })
  }
}
