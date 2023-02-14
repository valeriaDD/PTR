package Actors

import akka.actor.Actor

class ModifyMessageActor extends Actor{
  override def receive: Receive = {
    case message: Int => sender() ! (message + 1)
    case message: String => sender() ! message.toLowerCase
    case _ => sender() ! "Idk what to do with that"
  }
}
