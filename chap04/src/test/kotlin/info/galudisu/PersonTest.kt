package info.galudisu

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class PersonTest {
  @Test
  fun `Data class modifier makes value-object classes more convenient to use`() {
    val person = Person("Alice", 20)
    val another = person.copy(age = 21)
    assertNotEquals(another, person)
  }
}
