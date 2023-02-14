package Actors

import akka.actor.{Actor, ActorSystem, PoisonPill, Props, Terminated}

class MonitoredActor extends Actor {
  def receive: Receive = {
    case _ => println("I received a message")
  }
}

class MonitoredActorParent extends Actor{
  private val Johny = context.actorOf(Props[MonitoredActor], name = "Johny")
  context.watch(Johny)

  def receive: Receive = {
    case Terminated(Johny) => println("OMG, they killed Kenny")
    case _ => println("Parent received a message")
  }
}

object Monitoring extends App {
  private val actorSystem = ActorSystem("ActorSystem")

  // Watcher
  private val parentActor = actorSystem.actorOf(Props[MonitoredActorParent], "Parent")
  private val childActor = actorSystem.actorSelection("/user/Parent/Johny")
  childActor ! "Some text"
  childActor ! PoisonPill

  actorSystem.terminate()
}

