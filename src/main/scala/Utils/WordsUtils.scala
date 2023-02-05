package Utils

object WordsUtils {
  def translate(dict: Map[String, String], sentence: String): String = {
    val words = sentence.split(" ")
    val translatedWords = words.map(word => dict.getOrElse(word, word))
    translatedWords.mkString(" ")
  }
}
