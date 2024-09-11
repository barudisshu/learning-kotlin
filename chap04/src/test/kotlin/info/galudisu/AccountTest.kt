package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AccountTest {
  @Test
  fun `declaring a named companion object`() {
    val account1 = Account.Loader.fromJSON("""{"name": "Dmitry"}""".trimIndent())
    assertEquals("Dmitry", account1.name)
    val account2 = Account.fromJSON("""{"name": "Brent"}""".trimIndent())
    assertEquals("Brent", account2.name)
  }
}
