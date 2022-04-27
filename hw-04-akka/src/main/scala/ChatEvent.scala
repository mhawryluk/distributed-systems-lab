package homework.akka

import akka.actor.ActorRef

sealed trait ChatEvent
case class ChatMessage(username: String, message: String) extends ChatEvent
case class Connected(username: String, actorRef: ActorRef) extends ChatEvent
case class Disconnected(username: String) extends ChatEvent
