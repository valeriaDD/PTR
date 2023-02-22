package Actors

import akka.actor.{Actor, ActorSystem, Props}


class AccumulatorActor extends Actor {
  private var sum: Int = 0
  private var count: Int = 0

   def receive: Receive = {
    case number: Int =>
      sum += number
      count += 1
      println("Average: " + (sum.toDouble/count.toDouble))

    case _ =>
      println("I dont know how to process it")
   }
}

object AccumulatorActor extends App {
  private val actorSystem = ActorSystem("ActorSystem")

  private val parentActor = actorSystem.actorOf(Props[AccumulatorActor], "accumulator")

  parentActor ! 0
  parentActor ! 10
  parentActor ! "text"
  parentActor ! 10
  parentActor ! 10

  actorSystem.terminate()
}
