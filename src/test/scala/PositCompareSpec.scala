import org.scalatest.{Matchers, FlatSpec}
import hardposit.PositCompare
import chisel3._
import chisel3.util._
import chisel3.iotesters._

class PositCompareSpec extends ChiselFlatSpec {

  private class PositCompareTest(
      c: PositCompare,
      num1: Int,
      num2: Int,
      expectlt: Boolean,
      expecteq: Boolean,
      expectgt: Boolean
  ) extends PeekPokeTester(c) {
    poke(c.io.num1, num1)
    poke(c.io.num2, num2)
    step(1)
    expect(c.io.lt, expectlt)
    expect(c.io.eq, expecteq)
    expect(c.io.gt, expectgt)
  }

  private def test(
      nbits: Int,
      es: Int,
      num1: Int,
      num2: Int,
      expectlt: Boolean,
      expecteq: Boolean,
      expectgt: Boolean
  ): Boolean = {
    iotesters.Driver(() => new PositCompare(nbits, es)) { c =>
      new PositCompareTest(c, num1, num2, expectlt, expecteq, expectgt)
    }
  }

  it should "return less than when sign and exponent equal and fraction less" in {
    assert(
      test(
        8,
        0,
        0x62,
        0x6c,
        expectlt = true,
        expecteq = false,
        expectgt = false
      )
    ) // 2.25, 3.5
  }

  it should "return less than when sign one number negative and other is positive" in {
    assert(
      test(
        8,
        0,
        0xbc,
        0x20,
        expectlt = true,
        expecteq = false,
        expectgt = false
      )
    ) // -1.125, 0.5
  }

  it should "return less than when sign and fraction equal and exponent less" in {
    assert(
      test(
        8,
        2,
        0x62,
        0x66,
        expectlt = true,
        expecteq = false,
        expectgt = false
      )
    ) // 24, 48
  }

  it should "return less than when both numbers negative and magnitude of one number is grater than the other" in {
    assert(
      test(
        8,
        2,
        0xa6,
        0xad,
        expectlt = true,
        expecteq = false,
        expectgt = false
      )
    ) // -10, -5.5
  }

  it should "return equal when both numbers are positive and equal" in {
    assert(
      test(
        8,
        0,
        0x6c,
        0x6c,
        expectlt = false,
        expecteq = true,
        expectgt = false
      )
    ) // 3.5 ,3.5
  }

  it should "return equal when both numbers are negative and equal" in {
    assert(
      test(
        8,
        0,
        0xbc,
        0xbc,
        expectlt = false,
        expecteq = true,
        expectgt = false
      )
    ) // -1.125, -1.125
  }

  it should "return equal when both numbers are zero" in {
    assert(
      test(
        8,
        0,
        0x00,
        0x00,
        expectlt = false,
        expecteq = true,
        expectgt = false
      )
    ) // -1.125, -1.125
  }

  it should "return grater than when sign and exponent equal and fraction grater" in {
    assert(
      test(
        8,
        0,
        0x6c,
        0x62,
        expectlt = false,
        expecteq = false,
        expectgt = true
      )
    ) // 3.5, 2.25
  }

  it should "return grater than when sign and fraction equal and exponent grater" in {
    assert(
      test(
        8,
        2,
        0x66,
        0x62,
        expectlt = false,
        expecteq = false,
        expectgt = true
      )
    ) // 3.5, 2.25
  }
}
