package Utils

import org.scalatest.funsuite.AnyFunSuite

class NumberUtilsTest extends AnyFunSuite {
  test("13 is prime") {
    assert(NumberUtils.isPrime(13))
  }

  test("14 is not prime") {
    assert(!NumberUtils.isPrime(14))
  }

  test("smallest possible number") {
    val expected: Int = 123
    val actual = NumberUtils.createSmallestNumber(3, 1, 2)
    assert(expected == actual)
  }

  test("smallest possible number with 0") {
    val expected: Int = 109
    val actual = NumberUtils.createSmallestNumber(0, 1, 9)
    assert(expected == actual)
  }

  test("Get tuples where a^2 + b^2 = c^2") {
    val rightAngle = (3, 4, 5)
    val tuplesList = NumberUtils.listRightAngleTriangles();

    assert(tuplesList.contains(rightAngle))
  }

  test("Number factorization of prime") {
    val number = 13
    val expected = List(13)

    val actual = NumberUtils.primeFactorization(13)

    assert(expected.size == actual.size, "Lists have different sizes")
    assert(expected.zip(actual).forall(t => t._1 == t._2), "Lists have different elements")
  }

  test("Number factorization of number") {
    val number = 42
    val expected = List(7, 3, 2)

    val actual = NumberUtils.primeFactorization(42)

    assert(expected.size == actual.size, "Lists have different sizes")
    assert(expected.zip(actual).forall(t => t._1 == t._2), "Lists have different elements")
  }
}