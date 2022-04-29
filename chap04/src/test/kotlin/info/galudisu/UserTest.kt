package info.galudisu

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserTest {

  @Test
  fun `Accessing the backing field in a setter`() {
    val user = User("Alice")
    Assertions.assertEquals("unspecified", user.address)
    user.address = "Elsenheimerstrasse 47, 80687 Muenchen"
    Assertions.assertNotNull(user)
  }
}
