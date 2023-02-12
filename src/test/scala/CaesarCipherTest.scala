import org.scalatest.funsuite.AnyFunSuite

class CaesarCipherTest extends AnyFunSuite {
  test("Caesar encryption and decryption works fine") {
    val shift = 3
    val input = "lorem"
    val encoded = "oruhp"

    val actualEncode = CaesarCipher.encode(input, shift)
    val actualDecode = CaesarCipher.decode(encoded, shift)

    assert(input == actualDecode)
    assert(encoded == actualEncode)
  }
}
