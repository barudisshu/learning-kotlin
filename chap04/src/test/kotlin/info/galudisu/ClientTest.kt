package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClientTest {

  @Test
  fun `Universal object methods - toString()`() {
    val client = Client("Alice", 1234)
    assertEquals("Client(name=Alice, postalCode=1234)", client.toString())
  }

  @Test
  fun `Universal object methods - equals()`() {
    val client1 = Client("Alice", 1234)
    val client2 = Client("Alice", 1234)
    assertEquals(client1, client2)
  }

  @Test
  fun `Universal object methods - hashCode()`() {
    val processed = hashSetOf(Client("Alice", 1234))
    assertTrue(processed.contains(Client("Alice", 1234)))
  }
}

