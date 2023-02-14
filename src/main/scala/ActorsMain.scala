import Actors.{ModifyMessageActor, PrintMessageActor}
import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object ActorsMain extends App {
  implicit val timeout: Timeout = Timeout(5.seconds)
  private val actorSystem = ActorSystem("ActorSystem")

  private val printActor = actorSystem.actorOf(Props[PrintMessageActor], "printActor")
  private val modifierActor = actorSystem.actorOf(Props[ModifyMessageActor], "modifyActor")

  printActor ! "Hello, PTR!"

  (modifierActor ? 10).map { response =>
    println("Received : " + response)
  }

  (modifierActor ? "Some String").map { response =>
    println("Received : " + response)
  }
  (modifierActor ? {"obj" -> true} ).map { response =>
    println("Received : " + response)
  }

  actorSystem.terminate()
}