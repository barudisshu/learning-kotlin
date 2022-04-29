package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class TypeSystemTest {

  @Test
  fun `Using safe calls to deal with nullable properties`() {
    val ceo = Employee("Da Boss", null)
    val developer = Employee("Bob Smith", ceo)
    assertEquals("Da Boss", managerName(developer))
    assertNull(managerName(ceo))
  }

  @Test
  fun `Chaining multiple safe-call operators`() {
    val person = Person("Dmitry", null)
    assertEquals("Unknown", person.countryName())
  }
}
