package hardposit

import chisel3.iotesters

object EvalTests {

  def main(args: Array[String]): Unit = {
    val testArgs = args.slice(1, args.length).toArray
    args(0) match {
      case "FMAP16_add" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP16_add)(_)
      case "FMAP16_mul" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP16_mul)(_)
      case "FMAP16" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP16)(_)
      case "FMAP32_add" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP32_add)(_)
      case "FMAP32_mul" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP32_mul)(_)
      case "FMAP32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP32)(_)
      case "FMAP64_add" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP64_add)(_)
      case "FMAP64_mul" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP64_mul)(_)
      case "FMAP64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositFMAP64)(_)
      case "DivSqrtP16_div" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositDivSqrtP16_div
        )(_)
      case "DivSqrtP32_div" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositDivSqrtP32_div
        )(_)
      case "DivSqrtP64_div" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositDivSqrtP64_div
        )(_)
      case "DivSqrtP16_sqrt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositDivSqrtP16_sqrt
        )(_)
      case "DivSqrtP32_sqrt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositDivSqrtP32_sqrt
        )(_)
      case "DivSqrtP64_sqrt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositDivSqrtP64_sqrt
        )(_)
      case "CompareP16_lt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP16_lt
        )(_)
      case "CompareP32_lt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP32_lt
        )(_)
      case "CompareP64_lt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP64_lt
        )(_)
      case "CompareP16_eq" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP16_eq
        )(_)
      case "CompareP32_eq" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP32_eq
        )(_)
      case "CompareP64_eq" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP64_eq
        )(_)
      case "CompareP16_gt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP16_gt
        )(_)
      case "CompareP32_gt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP32_gt
        )(_)
      case "CompareP64_gt" =>
        iotesters.Driver.execute(
          testArgs,
          () => new Eval_PositCompareP64_gt
        )(_)
      case "P16toI32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP16toI32)(_)
      case "P16toI64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP16toI64)(_)
      case "P32toI32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP32toI32)(_)
      case "P32toI64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP32toI64)(_)
      case "P64toI32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP64toI32)(_)
      case "P64toI64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP64toI64)(_)
      case "P16toUI32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP16toUI32)(_)
      case "P16toUI64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP16toUI64)(_)
      case "P32toUI32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP32toUI32)(_)
      case "P32toUI64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP32toUI64)(_)
      case "P64toUI32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP64toUI32)(_)
      case "P64toUI64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP64toUI64)(_)
      case "I32toP16" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositI32toP16)(_)
      case "I64toP16" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositI64toP16)(_)
      case "I32toP32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositI32toP32)(_)
      case "I64toP32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositI64toP32)(_)
      case "I32toP64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositI32toP64)(_)
      case "I64toP64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositI64toP64)(_)
      case "UI32toP16" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositUI32toP16)(_)
      case "UI64toP16" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositUI64toP16)(_)
      case "UI32toP32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositUI32toP32)(_)
      case "UI64toP32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositUI64toP32)(_)
      case "UI32toP64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositUI32toP64)(_)
      case "UI64toP64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositUI64toP64)(_)
      case "P16toP32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP16toP32)(_)
      case "P16toP64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP16toP64)(_)
      case "P32toP16" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP32toP16)(_)
      case "P32toP64" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP32toP64)(_)
      case "P64toP16" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP64toP16)(_)
      case "P64toP32" =>
        iotesters.Driver
          .execute(testArgs, () => new Eval_PositP64toP32)(_)
    }
  }
}
