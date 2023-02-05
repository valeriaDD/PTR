package Utils

import org.scalatest.funsuite.AnyFunSuite

class ListUtilsTest extends AnyFunSuite {
  test("List of int is reversed") {
    val list = List(1, 2, 4, 8, 4)
    val expected = List(4, 8, 4, 2, 1)

    val actual = ListUtils.reverseList(list)

    assert(expected.size == actual.size, "Lists have different sizes")
    assert(expected.zip(actual).forall(t => t._1 == t._2), "Lists have different elements")
  }

  test("Sum of unique elements") {
    val list = List(1, 2, 4, 8, 4, 2)
    val expectedSum = 15

    val actualSum = ListUtils.sumOfUniqueElements(list)

    assert(actualSum == expectedSum, "Unique elements sum is different")
  }

  test("Extract random 3 elements from a list") {
    val list = List(1, 2, 4, 8, 4)
    val numberOfElements = 3

    val result = ListUtils.extractRandomElements(list, numberOfElements)

    assert(result.size == 3, "Lists have different sizes")
    assert(result.forall(list.contains), "Lists have different elements")
  }

  test("Extract more elements from a list than list size should throw exception") {
    val list = List(1, 2, 4, 8, 4)
    val numberOfElements = 12
    intercept[IllegalArgumentException] {
      ListUtils.extractRandomElements(list, numberOfElements)
    }
  }

  test("Rotate a list 3 places to the left") {
    val list = List(1 , 2, 4, 8, 4)
    val numberOfPlaces = 3
    val expected = List(8, 4, 1, 2, 4)

    val actual = ListUtils.rotateToLeft(list, numberOfPlaces)

    assert(expected.size == actual.size, "Lists have different sizes")
    assert(expected.zip(actual).forall(t => t._1 == t._2), "Lists have different elements")
  }

  test("Remove consecutive duplicated elements from list") {
    val list = List(1 , 2, 2, 2, 4, 8, 4)
    val expected = List(1, 2, 4, 8, 4)

    val actual = ListUtils.removeConsecutiveDuplicates(list)

    assert(expected.size == actual.size, "Lists have different sizes")
    assert(expected.zip(actual).forall(t => t._1 == t._2), "Lists have different elements")
  }
}
