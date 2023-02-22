import QueueAPI.{pop, push}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

case object Pop
case class Push(value: Any)

class QueueActor extends Actor {
  private val queue: List[Any] = List.empty

  def receive: Receive = onMessage(queue)

  private def onMessage(queue: List[Any]): Receive = {
    case Push(value) =>
      context.become(onMessage(queue :+ value))
    case Pop =>
      queue match {
        case Nil => sender() ! None
        case head :: tail =>
          context.become(onMessage(tail))
          sender() ! head
      }
  }
}

object QueueAPI {
  def push(queue: ActorRef, value: Any): Unit =
    queue ! Push(value)

  def pop(queue: ActorRef): Future[Unit]  = {
    implicit val timeout: Timeout = 5.seconds
    (queue ? Pop).map { response =>
      println("Received : " + response)
    }
  }
}

object QueueApp extends App {
  implicit val timeout: Timeout = Timeout(5.seconds)
  private val actorSystem = ActorSystem("ActorSystem")

  private val queueActor = actorSystem.actorOf(Props[QueueActor])
  push(queueActor, 10);
  push(queueActor, 3);
  pop(queueActor);
  pop(queueActor);
  pop(queueActor);

}
