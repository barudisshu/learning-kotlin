package info.galudisu

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DelegatingCollectionTest {

  @Test
  fun `delegating the implementation of the interface to another object with the by keyword`() {
    val delegatingCollection = DelegatingCollection(arrayListOf<Int>())
    assertTrue(delegatingCollection.isEmpty())
  }
}
