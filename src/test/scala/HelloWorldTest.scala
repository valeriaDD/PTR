import org.scalatest.funsuite.AnyFunSuite

class HelloWorldTest extends AnyFunSuite {
  test("Hello, world! should be printed") {
    val consoleOutput = new java.io.ByteArrayOutputStream()
    Console.withOut(consoleOutput) {
      HelloWorld.printHello()
    }

    assert(consoleOutput.toString.trim === "Hello, world!")
  }
}