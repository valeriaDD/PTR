package Actors

import akka.actor.Actor

class PrintMessageActor extends Actor {
   def receive: Receive = {
     case message => println(message);
   }
}
