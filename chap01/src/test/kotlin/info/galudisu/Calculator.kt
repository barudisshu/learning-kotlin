package info.galudisu

import kotlin.math.ln
import kotlin.math.sqrt

class Calculator {
  fun add(
    a: Int,
    b: Int,
  ) = a + b

  fun divide(
    a: Int,
    b: Int,
  ) = if (b == 0) {
    throw DivideByZeroException(a)
  } else {
    a / b
  }

  fun square(a: Int) = a * a

  fun squareRoot(a: Int) = sqrt(a.toDouble())

  fun log(
    base: Int,
    value: Int,
  ) = ln(value.toDouble()) / ln(base.toDouble())
}
