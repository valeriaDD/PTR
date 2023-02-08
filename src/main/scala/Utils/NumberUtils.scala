package Utils

import scala.annotation.tailrec

object NumberUtils {
  def isPrime(n: Int): Boolean = {
    if (n <= 1) return false
    for (i <- 2 to Math.sqrt(n).toInt) {
      if (n % i == 0) return false
    }
    true
  }

  def createSmallestNumber(a: Int, b: Int, c: Int): Int = {
    var digits = List(a, b, c).sorted

    if (digits.head == 0) {
      digits = digits.updated(0, digits(1)).updated(1, digits.head)
    }

    100 * digits.head + 10 * digits(1) + digits(2)
  }

  def listRightAngleTriangles(): List[(Int, Int, Int)] = {
    for {
      a <- 1 to 20
      b <- 1 to 20
      c <- 1 to 70
      if a * a + b * b == c * c
    } yield (a, b, c)
  }.toList

  def primeFactorization(n: Int): List[Int] = {
    def primeFactors(x: Int, factors: List[Int]): List[Int] = {
      if (isPrime(x)) x :: factors
      else {
        val nextFactor = (2 to x).find(x % _ == 0).get
        primeFactors(x / nextFactor, nextFactor :: factors)
      }
    }

    primeFactors(n, Nil)
  }
}
