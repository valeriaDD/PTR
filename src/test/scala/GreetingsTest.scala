import org.scalatest.funsuite.AnyFunSuite

class GreetingsTest extends AnyFunSuite {
  test("Hello, PTR! should be printed") {
    val consoleOutput = new java.io.ByteArrayOutputStream()
    Console.withOut(consoleOutput) {
      Greetings.printHello()
    }

    assert(consoleOutput.toString.trim === "Hello, PTR!")
  }
}