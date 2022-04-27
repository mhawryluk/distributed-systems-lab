package homework.akka

import akka.actor.ActorSystem


object Chat {

  private var chatRooms = Map.empty[String, Chatroom]

  def getChatroom(chatroom: String)(implicit actorSystem: ActorSystem): Chatroom = {
    chatRooms.getOrElse(chatroom, {
        val chatRoom = Chatroom(chatroom)
        chatRooms += chatroom -> chatRoom
        chatRoom
      })
  }
}
