package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class TreeTest {
  @Test
  fun `Balance Tree`() {
    val a = Tree<Int>() + 5 + 2 + 8
    assertEquals(1, a.height)
    assertEquals(3, a.size)
    assertEquals(5, a.value)
    val b = Tree<Int>() + 4 + 5 + 6 + 7
    assertEquals(3, b.height)
    assertEquals(4, b.value)
    val c = a.merge(b)
    assertEquals(5, c.value)
    val d = Tree(a, 3, b)
    assertEquals(3, d.value)
    val e = Tree(b, 3, a)
    assertEquals(3, e.value)
    val f = Tree(a, 3, Tree())
    assertEquals(3, f.value)
    val g = Tree(List(4, 3, 2, 1))
    assertEquals(4, g.value)
    assertFalse(g.isUnBalanced(f))
  }
}
