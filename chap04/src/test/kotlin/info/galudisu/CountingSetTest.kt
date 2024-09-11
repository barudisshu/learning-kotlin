package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CountingSetTest {
  @Test
  fun `using class delegation`() {
    val cset = CountingSet<Int>()
    cset.addAll(listOf(1, 1, 2))
    cset.add(2)
    assertEquals(4, cset.objectsAdded)
    assertEquals(2, cset.size)
  }
}
