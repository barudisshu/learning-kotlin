package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LengthCounterTest {
  @Test
  fun `declaring a property with a private setter`() {
    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!")
    assertEquals(3, lengthCounter.counter)
  }
}
