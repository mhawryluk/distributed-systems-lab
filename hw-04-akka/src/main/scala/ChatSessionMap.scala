package homework.akka

import akka.actor.typed.{ActorSystem, SpawnProtocol}

object ChatSessionMap {

  private var sessions: Map[String, ChatSession] = Map.empty[String, ChatSession]

  def findOrCreate(username: String)(implicit system: ActorSystem[SpawnProtocol.Command]): ChatSession = {
    sessions.getOrElse(username, create(username))
  }

  private def create(username: String)(implicit system: ActorSystem[SpawnProtocol.Command]): ChatSession = {
    val session = ChatSession(username)
    sessions += username -> session
    session
  }
}
