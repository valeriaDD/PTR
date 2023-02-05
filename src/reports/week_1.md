### Project for FAF.PTR16.1 Spring 2023 Course

### Dubina Valeria - FAF-203

---

## Week 1 of Project 0: Functional Programming / The Actor Model

## W1 - Welcome...

### Minimal Tasks:

1. Follow an installation guide to install the language / development environment of your choice. 
  
Chosen language for this project is SCALA and as development environment IntelliJ. 

2. Write a script that would print the message “Hello PTR” on the screen.
```scala
object Greetings {
  def printHello(): Unit = {
    println("Hello, PTR!")
  }
}
```

### Main Tasks:

1. Initialize a VCS repository for your project. Push your project to a remote repo.

HERE IT IS!

### Bonus Tasks:
1. Write a comprehensive readme for your repository.

Access the project [README.md](../../README.md)

2. Create a unit test for your project. Execute it.
```scala
import org.scalatest.funsuite.AnyFunSuite

class HelloWorldTest extends AnyFunSuite {
  test("Hello, PTR! should be printed") {
    val consoleOutput = new java.io.ByteArrayOutputStream()
    Console.withOut(consoleOutput) {
      Greetings.printHello()
    }

    assert(consoleOutput.toString.trim === "Hello, PTR!")
  }
}
```