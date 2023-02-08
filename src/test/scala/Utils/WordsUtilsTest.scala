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

  test("Only strings typed using one row of the letters on an English keyboard") {
    val list = List("Hello", "Alaska", "Dad", "Peace")
    val expected = List("Alaska", "Dad")

    val actual = WordsUtils.filterOneRowWords(list)
    println(actual)

    assert(expected.size == actual.size, "Lists have different sizes")
    assert(expected.zip(actual).forall(t => t._1 == t._2), "Lists have different elements")
  }

  test("Phone number combination fo 23") {
    val digitsToString = "23"
    val expected = List("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf")

    val actual = WordsUtils.phoneLettersCombinations(digitsToString)

    assert(expected.size == actual.size, "Lists have different sizes")
    assert(expected.zip(actual).forall(t => t._1 == t._2), "Lists have different elements")
  }

  test("Group anagrams") {
    val list = Array("eat", "tea", "tan", "ate", "nat", "bat")
    val actual = WordsUtils.groupAnagrams(list)
  }
}
