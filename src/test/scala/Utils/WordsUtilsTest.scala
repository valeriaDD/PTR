package Utils

import org.scalatest.funsuite.AnyFunSuite

class WordsUtilsTest extends AnyFunSuite {
  test("Dictionary is applied on sentence") {
    val dictionary = Map(
      "mama" -> "mother",
      "papa" -> "father",
    )
    val originalText = "mama is with papa"
    val expectedText = "mother is with father"

    val actualText = WordsUtils.translate(dictionary, originalText)

    assert(expectedText == actualText, "Expected and actual texts are different")
  }
}
