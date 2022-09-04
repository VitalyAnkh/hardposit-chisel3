import org.scalatest.{Matchers, FlatSpec}
import hardposit.ItoPConverter
import chisel3._
import chisel3.util._
import chisel3.iotesters._

class ItoPConverterSpec extends ChiselFlatSpec {

  private class ItoPConverterTest(
      c: ItoPConverter,
      integer: Int,
      unsigned: Boolean,
      expected: Int
  ) extends PeekPokeTester(c) {
    poke(c.io.integer, integer)
    poke(c.io.unsignedIn, unsigned)
    step(1)
    expect(c.io.posit, expected)
  }

  def test(
      nbits: Int,
      es: Int,
      integer: Int,
      unsigned: Boolean,
      expected: Int,
      intWidth: Int
  ): Boolean = {
    iotesters.Driver(() => new ItoPConverter(nbits, es, intWidth)) { c =>
      new ItoPConverterTest(c, integer, unsigned, expected)
    }
  }

  it should "return posit value for unsigned integer" in {
    assert(test(8, 0, 2, unsigned = true, 0x60, 8))
  }

  it should "return posit value for unsigned integer 2" in {
    assert(test(8, 2, 5, unsigned = true, 0x52, 8))
  }

  it should "return posit value for unsigned integer 3" in {
    assert(test(16, 2, 475, unsigned = true, 0x71b6, 16))
  }

  it should "return posit value for signed integer" in {
    assert(test(8, 0, 0xf6, unsigned = false, 0x87, 8))
  }

  it should "return posit value for signed integer 2" in {
    assert(test(8, 2, 0xb0, unsigned = false, 0x97, 8))
  }

  it should "return posit value for wider unsigned integer " in {
    assert(test(8, 2, 32896, unsigned = true, 0x7b, 16))
  }

  it should "return posit value for wider signed integer " in {
    assert(test(8, 2, 0xffb0, unsigned = false, 0x97, 16))
  }

  it should "return posit value for narrower unsigned integer " in {
    assert(test(16, 2, 127, unsigned = true, 0x6bf0, 8))
  }

  it should "return posit value for narrower signed integer " in {
    assert(test(16, 2, 0xb0, unsigned = false, 0x9700, 8))
  }

  it should "return NaR for integer with an MSB of 1 and all other bits 0" in {
    assert(test(16, 2, 0x80, unsigned = false, 0x8000, 8))
  }
}
