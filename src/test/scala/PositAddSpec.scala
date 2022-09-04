import hardposit.PositAdd
import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}
class PositAddSpec extends FlatSpec {

  private class PositAddTest(
      c: PositAdd,
      num1: Int,
      num2: Int,
      expectedPosit: Int,
      sub: Boolean,
      isNaR: Boolean
  ) extends PeekPokeTester(c) {
    poke(c.io.num1, num1)
    poke(c.io.num2, num2)
    poke(c.io.sub, sub)
    step(1)
    expect(c.io.out, expectedPosit)
    expect(c.io.isNaR, isNaR)
  }

  private def test(
      nbits: Int,
      es: Int,
      num1: Int,
      num2: Int,
      expectedPosit: Int,
      sub: Boolean = false,
      isNaR: Boolean = false
  ): Boolean = {
    iotesters.Driver(() => new PositAdd(nbits, es)) { c =>
      new PositAddTest(c, num1, num2, expectedPosit, sub, isNaR)
    }
  }

  it should "return added value when both exponent and signs are equal" in {
    assert(test(8, 0, 0x6c, 0x62, 0x74))
  }

  it should "return added value when both exponent and signs are equal1" in {
    assert(test(8, 0, 0x6d, 0x6a, 0x76))
  }

  it should "return added value when both exponent and signs are equal2" in {
    assert(test(8, 1, 0x5d, 0x5b, 0x66))
  }

  it should "return added value when both exponents are not equal but sign is positive" in {
    assert(test(16, 1, 0x6d00, 0x5b00, 0x7018))
  }

  it should "return added value when both exponent and signs are equal3" in {
    assert(test(8, 0, 0x94, 0x9e, 0x8c))
  }

  it should "return the subtracted value when first one is lesser and positive" in {
    assert(test(8, 1, 0x42, 0xac, 0xba))
  }

  it should "return the subtracted value when first one is greater and positive" in {
    assert(test(8, 1, 0x54, 0xbe, 0x46))
  }

  it should "return the zero when both values are equal and different signs" in {
    assert(test(8, 0, 0x9d, 0x63, 0x00))
    assert(test(8, 1, 0x9d, 0x63, 0x00))
    assert(test(8, 2, 0x9d, 0x63, 0x00))
  }

  it should "return the subtracted value when first one is greater and positive1" in {
    assert(test(16, 2, 0x64af, 0xaf44, 0x6423))
  }

  it should "return the subtracted value when first one is lesser and negative1" in {
    assert(test(16, 2, 0xaf44, 0x64af, 0x6423))
  }

  it should "return the subtracted value when first one is greater and negative" in {
    assert(test(16, 2, 0x9b51, 0x50bc, 0x9bdd))
  }

  it should "return the maxpos when the exponent are at max" in {
    assert(test(8, 0, 0x7f, 0x7f, 0x7f))
  }

  it should "return the other number when one of it is zero" in {
    assert(test(8, 2, 0, 0x64, 0x64))
    assert(test(8, 2, 0x73, 0, 0x73))
  }

  it should "return infinite number when one of it is infinite" in {
    assert(test(8, 1, 0x80, 0x64, 0x80, isNaR = true))
    assert(test(8, 1, 0x74, 0x80, 0x80, isNaR = true))
  }

  it should "return infinite infinity when both are infinity" in {
    assert(test(8, 2, 0x80, 0x80, 0x80, isNaR = true))
  }

  it should "return zero when both are zero" in {
    assert(test(8, 4, 0, 0, 0))
  }

  it should "for 100MHZ checking in fpga 8 * 0" in {
    assert(test(8, 0, 64, 72, 98))
    assert(test(8, 0, 98, 76, 109))
    assert(test(8, 0, 109, 80, 114))
    assert(test(8, 0, 114, 82, 117))
    assert(test(8, 0, 117, 84, 120))
    assert(test(8, 0, 120, 86, 121))
    assert(test(8, 0, 120, 88, 121))
    assert(test(8, 0, 120, 89, 121))
    assert(test(8, 0, 120, 0xa6, 116))
  }

  it should "for 100MHZ checking in fpga 8 * 2" in {
    assert(test(8, 2, 64, 72, 76))
    assert(test(8, 2, 76, 76, 84))
    assert(test(8, 2, 84, 80, 90))
    assert(test(8, 2, 90, 82, 95))
    assert(test(8, 2, 95, 84, 97))
    assert(test(8, 2, 97, 86, 99))
    assert(test(8, 2, 98, 88, 100))
    assert(test(8, 2, 100, 89, 101))
    assert(test(8, 2, 0xa6, 101, 100))
  }

  it should "for 100MHZ checking in 16*1" in {
    assert(test(16, 1, 0x4000, 0x5000, 0x5800))
    assert(test(16, 1, 0x5800, 0x5800, 0x6400))
    assert(test(16, 1, 0x6400, 0x6000, 0x6a00))
    assert(test(16, 1, 0x6a00, 0x6200, 0x6f00))
    assert(test(16, 1, 0x6f00, 0x6400, 0x7140))
    assert(test(16, 1, 0x7140, 0x6600, 0x7300))
    assert(test(16, 1, 0x7300, 0x6800, 0x7480))
    assert(test(16, 1, 0x7480, 0x6900, 0x75a0))
    assert(test(16, 1, 0x75a0, 0x9600, 0x7460))
  }

  it should "return zero for the same posit numbers" in {
    assert(test(8, 1, 0x93, 0x93, 0x00, sub = true))
  }

  it should "return isNaN as true when both are infinity" in {
    assert(test(8, 4, 0x80, 0x80, 0x80, sub = true, isNaR = true))
  }

  it should "return infinity when one of it is infinity" in {
    assert(test(16, 2, 0x8000, 0x7543, 0x8000, sub = true, isNaR = true))
    assert(test(16, 2, 0x7543, 0x8000, 0x8000, sub = true, isNaR = true))
  }

  it should "return the first number when the second number is zero" in {
    assert(test(8, 3, 0x64, 0x00, 0x64, sub = true))
  }

  it should "return the negative of second number when the first number is zero" in {
    assert(test(8, 3, 0, 0x45, 0xbb, sub = true))
  }

  it should "return the addition when both are having different signs" in {
    assert(test(8, 1, 0x5d, 0xa5, 0x66, sub = true))
  }

  it should "return the positive number when both are of same sign and first number is larger" in {
    assert(test(8, 1, 0x54, 0x42, 0x46, sub = true))
    assert(test(8, 1, 0xbe, 0xac, 0x46, sub = true))
  }

  it should "return the negative number when both are of same sign and second number is larger" in {
    assert(test(8, 1, 0x42, 0x54, 0xba, sub = true))
    assert(test(8, 1, 0xac, 0xbe, 0xba, sub = true))
  }
}
