package Actors

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorSystem, DeathPactException, OneForOneStrategy, Props, SupervisorStrategy}

case class StartProcessing(msg: String)
case class ProcessInput(msg: Any)
case class SplitResult(msg: Any)
case class SpawnedResult(msg: Any)
case class JoinedResult(msg: Any)

class Supervisor extends Actor {

  private val splitter = context.actorOf(Props[SplitActor], name = "splitter")
  context.watch(splitter)

  private val spawner = context.actorOf(Props[SwapAndLowercaseActor], name = "spawner")
  context.watch(spawner)

  private val joiner = context.actorOf(Props[JoinActor], name = "joiner")
  context.watch(joiner)

  private val printer = context.actorOf(Props[PrinterActor], name = "printer")
  context.watch(printer)


  override def receive: Receive = {
    case StartProcessing(msg) => splitter ! ProcessInput(msg)
    case SplitResult(msg) => spawner ! ProcessInput(msg)
    case SpawnedResult(msg) => joiner ! ProcessInput(msg)
    case JoinedResult(msg) => printer ! ProcessInput(msg)
  }

  override def supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy() {
      case _: Exception =>
        println("Worker actor encountered an exception, restarting...")
        Restart
      case _: DeathPactException =>
        println("Worker actor encountered an exception, restarting...")
        Restart
    }
}

class SplitActor() extends Actor {
  def receive: Receive = {
    case ProcessInput(input: String) =>
      val splitString = input.split("\\s+")
      println(s"Message split: ${splitString.mkString("Array(", ", ", ")")}")
      sender() ! SplitResult(splitString)
  }
}

class SwapAndLowercaseActor() extends Actor {
  def receive: Receive = {
    case ProcessInput(splitString: Array[String]) =>
      val cleanedString = splitString.map(
        _.toLowerCase()
        .replace("n", "*****")
        .replace("m", "n")
        .replace("*****", "m")
      )
      println(s"Message spawn: ${cleanedString.mkString("Array(", ", ", ")")}")
      sender() ! SpawnedResult(cleanedString)
  }
}

class JoinActor() extends Actor {
  def receive: Receive = {
    case ProcessInput(cleanedString: Array[String]) =>
      val joinedString = cleanedString.mkString(" ")
      println(s"Message joined: $joinedString")
      sender() ! JoinedResult(joinedString)
  }
}

class PrinterActor() extends Actor {
  def receive: Receive = {
    case ProcessInput(cleanedString) =>
      println(s"Printing message: $cleanedString")
  }
}

object SupervisedProcessingLine extends App {
  val system = ActorSystem("SupervisedProcessingLine")

  private val supervisor = system.actorOf(Props(classOf[Supervisor]), "supervisor")

  supervisor ! StartProcessing("you nomster!")
  supervisor ! StartProcessing("you nomster2!")
}
