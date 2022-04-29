package info.galudisu

import java.time.LocalDate

data class Point(val x: Int, val y: Int)

operator fun Point.plus(other: Point): Point {
  return Point(x + other.x, y + other.y)
}


operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> = object : Iterator<LocalDate> {
  var current = start
  override fun hasNext(): Boolean = current <= endInclusive
  override fun next(): LocalDate = current.apply { current = plusDays(1) }
}

fun printEntries(map: Map<String, String>) {
  for ((key, value) in map) {
    println("$key -> $value")  // Destructuring declaration in a loop
  }
}
