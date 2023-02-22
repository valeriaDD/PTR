package Actors

import akka.actor.{Actor, ActorSystem, Props}

class PrintMessageActor extends Actor {
   def receive: Receive = {
     case message => println(message);
   }
}

object PrintMessageActor extends App {
  private val actorSystem = ActorSystem("ActorSystem")
  private val printActor = actorSystem.actorOf(Props[PrintMessageActor], "printActor")

  printActor ! "Hello, PTR!"
  actorSystem.terminate()
}