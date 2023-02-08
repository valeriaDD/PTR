object CaesarCipher {
  def encode(s: String, shift: Int): String = {
    s.map(c => (c + shift).toChar).mkString
  }

  def decode(s: String, shift: Int): String = {
    s.map(c => (c - shift).toChar).mkString
  }
}
