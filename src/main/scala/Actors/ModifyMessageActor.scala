package Actors

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

class ModifyMessageActor extends Actor{
  override def receive: Receive = {
    case message: Int => sender() ! (message + 1)
    case message: String => sender() ! message.toLowerCase
    case _ => sender() ! "Idk what to do with that"
  }
}

object ModifyMessageActor extends App {
  implicit val timeout: Timeout = Timeout(5.seconds)
  private val actorSystem = ActorSystem("ActorSystem")

  private val modifierActor = actorSystem.actorOf(Props[ModifyMessageActor], "modifyActor")

  (modifierActor ? 10).map { response =>
    println("Received : " + response)
  }

  (modifierActor ? "Some String").map { response =>
    println("Received : " + response)
  }
  (modifierActor ? {
    "obj" -> true
  }).map { response =>
    println("Received : " + response)
  }

  actorSystem.terminate()
}
