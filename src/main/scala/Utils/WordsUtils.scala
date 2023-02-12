package Utils

object WordsUtils {
  def translate(dict: Map[String, String], sentence: String): String = {
    val words = sentence.split(" ")
    val translatedWords = words.map(word => dict.getOrElse(word, word))
    translatedWords.mkString(" ")
  }

  def filterOneRowWords(words: List[String]): List[String] = {
    val row1 = Set('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p')
    val row2 = Set('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l')
    val row3 = Set('z', 'x', 'c', 'v', 'b', 'n', 'm')

    words.filter { word =>
      val lowerCaseWord = word.toLowerCase()

      lowerCaseWord.forall(c => row1.contains(c)) ||
        lowerCaseWord.forall(c => row2.contains(c)) ||
        lowerCaseWord.forall(c => row3.contains(c))
    }
  }

  def phoneLettersCombinations(digits: String): List[String] = {
    val digitMap = Map(
      '2' -> "abc",
      '3' -> "def",
      '4' -> "ghi",
      '5' -> "jkl",
      '6' -> "mno",
      '7' -> "pqrs",
      '8' -> "tuv",
      '9' -> "wxyz"
    )

    def helper(digits: List[Char], current: String, result: List[String]): List[String] = {
      if (digits.isEmpty) {
        return result :+ current // push to end
      }
      val letters = digitMap(digits.head)

      letters.flatMap(letter => helper(digits.tail, current + letter, result)).toList
    }

    if (digits.isEmpty) {
      List()
    }
    else {
      helper(digits.toList, "", List())
    }
  }

  def groupAnagrams(words: Array[String]): List[Array[String]] = {
    val map = words.groupBy(_.sorted)
    map.foreach(kv => println(s"${kv._1}: ${kv._2.mkString(", ")}")) // just to print
    map.values.toList
  }
}
