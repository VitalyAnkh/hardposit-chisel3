import org.scalatest.{Matchers, FlatSpec}
import hardposit.PositExtractor
import chisel3._
import chisel3.util._
import chisel3.iotesters._

class PositExtractorSpec extends ChiselFlatSpec {

  private class PositExtractorTest(
      c: PositExtractor,
      num: Int,
      sign: Boolean,
      exponent: Int,
      fraction: Int
  ) extends PeekPokeTester(c) {
    poke(c.io.in, num)
    expect(c.io.out.sign, sign)
    expect(c.io.out.exponent, exponent)
    expect(c.io.out.fraction, fraction)
  }

  private def test(
      nbits: Int,
      es: Int,
      num: Int,
      sign: Boolean,
      exponent: Int,
      fraction: Int
  ): Boolean = {
    iotesters.Driver(() => new PositExtractor(nbits, es)) { c =>
      new PositExtractorTest(c, num, sign, exponent, fraction)
    }
  }

  it should "return the sign, exponent, fraction as expected for positive number" in {
    assert(test(8, 2, 0x36, sign = false, -2, 0xe))
  }

  it should "return the sign, exponent, fraction as expected for negative number" in {
    assert(test(8, 1, 0xb2, sign = true, 0, 0x1e))
  }
}
