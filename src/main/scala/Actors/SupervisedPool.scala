package Actors

import akka.actor.{Actor, ActorSystem, PoisonPill, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

import java.lang.Thread.sleep


case class Work(msg: String)
case object KillWorker

class Master(numWorkers: Int) extends Actor {
  var router: Router = {
    val routees = Vector.fill(numWorkers) {
      val r = context.actorOf(Props[Worker]())
      context.watch(r)
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive: Receive = {
    case w: Work =>
      router.route(w, sender())

    case KillWorker =>
      println("Killing the next available actor!")
      router.route(PoisonPill, sender())

    case Terminated(actor) =>
      println(s"${actor.path} was killed, recreating new one")
      router = router.removeRoutee(actor)
      val reference = context.actorOf(Props[Worker]())
      context.watch(reference)
      router = router.addRoutee(reference)
  }
}

class Worker extends Actor {
  override def receive: Receive = {
    case Work(msg) => println(s"${self.path} received message: $msg")
  }
}

object SupervisedPool extends App {
  private val system = ActorSystem("TestSystem")

  private val master = system.actorOf(Props(classOf[Master], 2), "master")

  master ! Work("Message 1")
  sleep(1000)
  master ! Work("Message 2")
  sleep(1000)
  master ! Work("Message 3")
  sleep(1000)
  master ! KillWorker
  sleep(1000)
  master ! Work("Message 4")
  master ! Work("Message 5")
  master ! Work("Message 6")
  master ! Work("Message 7")

  system.terminate()
}
