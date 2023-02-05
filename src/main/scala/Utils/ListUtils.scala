package Utils

import scala.util.Random

object ListUtils {
  def reverseList[dataType](list: List[dataType]): List[dataType] = {
    list.reverse
  }

  def sumOfUniqueElements(list: List[Int]): Int = {
    val uniqueElements = list.toSet
    uniqueElements.sum
  }

  def extractRandomElements[dataType](list: List[dataType], numberOfElements: Int): List[dataType] = {
    if (numberOfElements > list.size) {
      throw new IllegalArgumentException("The list doesn't have enough elements")
    }

    val random = new Random
    (0 until numberOfElements).map(i => list(random.nextInt(list.length))).toList
  }

  def rotateToLeft[dataType](list: List[dataType], numberOfPlaces: Int = 1): List[dataType] = {
    val rotated = numberOfPlaces % list.length
    list.drop(rotated) ++ list.take(rotated)
  }

  def removeConsecutiveDuplicates[A](list: List[A]): List[A] = {
    list.foldRight(List[A]()) { (currentElement, accumulatedFromPreIteration) =>
      if (accumulatedFromPreIteration.isEmpty || accumulatedFromPreIteration.head != currentElement) {
        currentElement :: accumulatedFromPreIteration
      }
      else {
        accumulatedFromPreIteration
      }
    }
  }
}