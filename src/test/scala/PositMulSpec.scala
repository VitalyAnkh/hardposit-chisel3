import org.scalatest.{Matchers, FlatSpec}
import hardposit.PositMul
import chisel3._
import chisel3.util._
import chisel3.iotesters._

class PositMulSpec extends ChiselFlatSpec {

  private class PositMulTest(
      c: PositMul,
      num1: Int,
      num2: Int,
      expectedPosit: Int,
      isNaR: Boolean
  ) extends PeekPokeTester(c) {
    poke(c.io.num1, num1)
    poke(c.io.num2, num2)
    step(1)
    expect(c.io.isNaR, isNaR)
    expect(c.io.out, expectedPosit)
  }

  private def test(
      nbits: Int,
      es: Int,
      num1: Int,
      num2: Int,
      expectedPosit: Int,
      isNaR: Boolean = false
  ): Boolean = {
    iotesters.Driver(() => new PositMul(nbits, es)) { c =>
      new PositMulTest(c, num1, num2, expectedPosit, isNaR)
    }
  }

  it should "return multiplied value signs and exponent signs are positive" in {
    assert(test(16, 1, 0x5a00, 0x6200, 0x7010))
  }

  it should "return multiplied value when signs are different" in {
    assert(test(16, 1, 0xa600, 0x6200, 0x8ff0))
    assert(test(16, 1, 0x6200, 0xa600, 0x8ff0))
  }

  it should "return the zero when one of the number is zero" in {
    assert(test(8, 0, 0x6f, 0x00, 0x00))
    assert(test(8, 0, 0x00, 0x32, 0x00))
  }

  it should "return infinity when one of the number is infinite" in {
    assert(test(8, 1, 0x80, 0x43, 0x80, isNaR = true))
    assert(test(8, 1, 0x43, 0x80, 0x80, isNaR = true))
  }

  it should "return isNan as true when one is zero and another one is infinity" in {
    assert(test(16, 7, 0x8000, 0, 0x8000, isNaR = true))
    assert(test(16, 7, 0, 0x8000, 0x8000, isNaR = true))
  }

  it should "return the positive number when there are two negative numbers multiplied" in {
    assert(test(16, 1, 0xa600, 0x9e00, 0x7010))
  }

  it should "return the correct multiplication when of the number has smallest exponent" in {
    assert(test(8, 2, 0x47, 0x10, 0x14))
  }
}
