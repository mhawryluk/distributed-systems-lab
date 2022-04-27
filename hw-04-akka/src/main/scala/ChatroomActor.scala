package homework.akka

import akka.actor.{Actor, ActorRef}


class ChatroomActor extends Actor {

  private var users: Map[String, ActorRef] = Map.empty

  override def receive: Receive = {
    case Connected(username, actorRef) =>
      users += username -> actorRef
      sendMessage(ChatMessage(username, "CONNECTED"))

    case message: ChatMessage => sendMessage(message)

    case Disconnected(username) =>
      users -= username
      sendMessage(ChatMessage(username, "DISCONNECTED"))

    case other => println(other)
  }

  private def sendMessage(message: ChatMessage): Unit = {
    users.values.foreach(_ ! message)
  }
}
