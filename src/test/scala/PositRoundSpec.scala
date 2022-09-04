import org.scalatest.{Matchers, FlatSpec}
import hardposit.PositRound
import chisel3._
import chisel3.util._
import chisel3.iotesters._

class PositRoundSpec extends ChiselFlatSpec {

  private class PositRoundTest(
      c: PositRound,
      inSign: Boolean,
      inExp: Int,
      inFrac: Int,
      inIsNaR: Boolean,
      inIsZero: Boolean,
      inTrailingBits: Int,
      inStickyBit: Boolean,
      expectExp: Int,
      expectFrac: Int
  ) extends PeekPokeTester(c) {
    poke(c.io.in.sign, inSign)
    poke(c.io.in.exponent, inExp)
    poke(c.io.in.fraction, inFrac)
    poke(c.io.in.isNaR, inIsNaR)
    poke(c.io.in.isZero, inIsZero)
    poke(c.io.trailingBits, inTrailingBits)
    poke(c.io.stickyBit, inStickyBit)
    step(1)
    expect(c.io.out.exponent, expectExp)
    expect(c.io.out.fraction, expectFrac)
  }

  private def test(
      nbits: Int,
      es: Int,
      inSign: Boolean,
      inExp: Int,
      inFrac: Int,
      inIsNaR: Boolean,
      inIsZero: Boolean,
      inTrailingBits: Int,
      inStickyBit: Boolean,
      expectExp: Int,
      expectFrac: Int
  ): Boolean = {
    iotesters.Driver.execute(
      Array(
        "--generate-vcd-output",
        "on",
        "--target-dir",
        "test_run_dir/round",
        "--top-name",
        "round"
      ),
      () => new PositRound(nbits, es)
    ) { c =>
      new PositRoundTest(
        c,
        inSign,
        inExp,
        inFrac,
        inIsNaR,
        inIsZero,
        inTrailingBits,
        inStickyBit,
        expectExp,
        expectFrac
      )
    }
  }

  it should "round up the posit 1" in {
    assert(test(16, 1, false, 4, 0x0af4, false, false, 3, true, 4, 0x0af5))
  }

  it should "round up the posit 2" in {
    assert(test(16, 1, false, 4, 0x0af1, false, false, 2, true, 4, 0x0af2))
  }

  it should "round down the posit 1" in {
    assert(test(16, 1, false, 4, 0x0af4, false, false, 0, false, 4, 0x0af4))
  }

  it should "round down the posit 2" in {
    assert(test(16, 1, false, 4, 0x0af4, false, false, 2, false, 4, 0x0af4))
  }

  it should "fraction overflow must be carried over to exponent" in {
    assert(
      test(16, 1, false, 0x0a, 0x1fff, false, false, 3, true, 0x0b, 0x1000)
    )
  }

  it should "round to maxpos if overflow" in {
    assert(
      test(16, 1, false, 0x1c, 0x1fff, false, false, 3, true, 0x1c, 0x1000)
    )
  }

  it should "round to minpos if underflow" in {
    assert(
      test(16, 1, false, -0x1d, 0x1fff, false, false, 0, false, -0x1c, 0x1000)
    )
  }
}
