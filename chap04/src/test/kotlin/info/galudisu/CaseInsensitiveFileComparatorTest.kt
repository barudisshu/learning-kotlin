package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class CaseInsensitiveFileComparatorTest {

  @Test
  fun `implementing Comparator with an object`() {
    val files = listOf(File("/Z"), File("/a"))
    val comparator = files.sortedWith(CaseInsensitiveFileComparator)
    assertEquals(2, comparator.size)
  }
}

